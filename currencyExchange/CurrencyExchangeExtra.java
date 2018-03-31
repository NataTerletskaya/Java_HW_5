package currencyExchange;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;

import currencyExchange.enums.CurrencyName;
import currencyExchange.enums.OperationName;
import currencyExchange.enums.OperationStep;
import currencyExchange.exceptions.CurrencyExchangeException;

public class CurrencyExchangeExtra {

	static private OperationStep currentStep = OperationStep.START;
	static private Scanner scan = new Scanner(System.in);
	static private RateConfiguration config = new RateConfiguration();
	static private CurrencyRate c = new CurrencyRate(config.rates, CurrencyName.UAH);
	// declare values
	static private OperationName operation = OperationName.SELL;
	static private CurrencyName currencyToSell = CurrencyName.UAH;
	static private CurrencyName currencyToBuy = CurrencyName.UAH;
	static private BigDecimal sum = new BigDecimal("0");
	static private BigDecimal rate;
	static private BigDecimal operationResult;
	static private ArrayList<String> history = new ArrayList<>();

	public static void main(String[] args) {
		System.out.println("Hello, Dear Customer!");
		System.out.println("This is a Cross Currency Calculator");

		do {
			if (currentStep == OperationStep.START) {
				System.out.println("****************************");
				System.out.println("*          START           *");
				System.out.println("****************************");
				infoCurrencyRate();
				System.out.println("What would you like to do?");
				currentStep = OperationStep.SELECT_OPERATION;
			}
			// 1. choose the operation
			if (currentStep == OperationStep.SELECT_OPERATION) {
				try {
					chooseOperation();
					currentStep = OperationStep.SELECT_CURRENCY_FROM;
				} catch (CurrencyExchangeException e) {
					System.out.println("[ERROR]: " + e.getMessage());
				} catch (Exception e) {
					System.out.println("Wrong input, please try again.");
				}
			}
			// 2. select currency from
			if (currentStep == OperationStep.SELECT_CURRENCY_FROM) {
				try {
					runStepSelectCurrencyFrom();
					currentStep = OperationStep.SELECT_SUM;
				} catch (CurrencyExchangeException e) {
					System.out.println("[ERROR]: " + e.getMessage());
				} catch (Exception e) {
					System.out.println("Wrong input, please try again.");
				}
			}
			// 3. input sum
			if (currentStep == OperationStep.SELECT_SUM) {
				try {
					runStepSum();
					currentStep = OperationStep.SELECT_CURRENCY_TO;
				} catch (CurrencyExchangeException e) {
					System.out.println("[ERROR]: " + e.getMessage());
				} catch (Exception e) {
					System.out.println("Wrong input, please try again.");
				}
			}
			// 4. select currency to
			if (currentStep == OperationStep.SELECT_CURRENCY_TO) {
				try {
					runStepSelectCurrencyTo();
					currentStep = OperationStep.RESULT;
				} catch (CurrencyExchangeException e) {
					System.out.println("[ERROR]: " + e.getMessage());
				} catch (Exception e) {
					System.out.println("Wrong input, please try again.");
				}
			}

			if (currentStep == OperationStep.RESULT) {

				try {
					runStepResult();
					currentStep = OperationStep.CONTINUE;
				} catch (CurrencyExchangeException e) {
					System.out.println("[ERROR]: " + e.getMessage());
				} catch (Exception e) {
					System.out.println("Wrong input, please try again.");
				}
			}

			if (currentStep == OperationStep.CONTINUE) {

				try {
					boolean yes = continueWork();
					if (yes) {

						currentStep = OperationStep.START;
					} else {
						currentStep = OperationStep.END;
					}

				} catch (CurrencyExchangeException e) {
					System.out.println("[ERROR]: " + e.getMessage());
				} catch (Exception e) {
					System.out.println("Wrong input, please try again.");
				}
			}

		} while (currentStep != OperationStep.END);

		try {
			showHistory();
		} catch (CurrencyExchangeException e) {
			System.out.println("[ERROR]: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Wrong input, please try again.");
		}

		System.out.println("_________THE END_________");
		System.out.println("*************************");
	}

	private static void runStepResult() throws CurrencyExchangeException {
		calculateResult();

		String message = "";

		if (operation == OperationName.BUY) {
			message = "You have to pay " + operationResult + " " + currencyToSell.toString();
		} else if (operation == OperationName.SELL) {
			message = "You will get " + operationResult + " " + currencyToBuy.toString();
		} else {
			throw new CurrencyExchangeException("Invalid operation!");
		}

		System.out.println(">> " + message + " <<");

		history.add(getHistoryString());
	}

	private static void runStepSelectCurrencyTo() throws CurrencyExchangeException {
		switch (operation) {
		case BUY:
			System.out.println("What currency do you have?");
			// input currency to buy
			currencyToSell = chooseCurrency();
			break;

		case SELL:
			System.out.println("What currency would you like to get?");
			// input currency to sell
			currencyToBuy = chooseCurrency();

			break;
		default:
			break;
		}

		if (currencyToSell.equals(currencyToBuy)) {
			throw new CurrencyExchangeException("The currency must be different");
		}

		currentStep = OperationStep.RESULT;
	}

	private static void runStepSelectCurrencyFrom() throws CurrencyExchangeException {
		switch (operation) {
		case BUY:
			// input currency to sell
			currencyToBuy = chooseCurrency();
			break;
		case SELL:
			// input currency to buy
			currencyToSell = chooseCurrency();
			break;
		}
	}

	private static void runStepSum() throws CurrencyExchangeException {
		System.out.println("Enter sum (sum must be > 0):");

		String input = scan.next();

		sum = new BigDecimal(input);

		if (sum.signum() <= 0) {
			throw new CurrencyExchangeException("Wrong sum! Try again! Sum must be > 0");
		}
	}

	private static boolean continueWork() throws CurrencyExchangeException {
		// another operation?
		System.out.println("\n_________________________");
		System.out.println("Do you want to continue work? ===>>");
		System.out.println("Yes >> type 1");
		System.out.println("No  >> type any other key");

		String input = scan.next();
		boolean result = true;

		if (false == input.equals("1")) {
			result = false;
		}

		return result;
	}

	private static void chooseOperation() throws CurrencyExchangeException {
		System.out.println("Input the operation (" + CurrencyRate.getOperationList() + ") >>");
		String choice = scan.next();

		try {
			operation = OperationName.valueOf(choice.toUpperCase());
		} catch (Exception e) {
			throw new CurrencyExchangeException("Invalid operation");
		}
	}

	private static CurrencyName chooseCurrency() throws CurrencyExchangeException {
		System.out.println("Input the currency (" + CurrencyRate.getCurrencyNameList() + ") >>");
		String choice = scan.next();
		CurrencyName currency = CurrencyName.UAH;

		try {
			currency = CurrencyName.valueOf(choice.toUpperCase());
		} catch (Exception e) {
			throw new CurrencyExchangeException("Invalid currency");
		}

		return currency;
	}

	private static void calculateResult() {
		rate = new BigDecimal(c.getRate(currencyToSell, currencyToBuy));

		// calculate result
		switch (operation) {
		case BUY:
			if (currencyToSell == CurrencyName.UAH) {
				operationResult = rate.multiply(sum).setScale(2, BigDecimal.ROUND_FLOOR);
			} else {
				operationResult = sum.divide(rate, 2, RoundingMode.HALF_UP).setScale(2, BigDecimal.ROUND_FLOOR);
			}
			break;
		case SELL:
			operationResult = rate.multiply(sum).setScale(2, BigDecimal.ROUND_FLOOR);
			break;
		}
	}

	// History
	private static String getHistoryString() {
		String result = "";

		switch (operation) {
		case BUY:
			result = (operation.toString() + " " + sum + " " + currencyToBuy + " to " + currencyToSell);
			break;
		case SELL:
			result = (operation.toString() + " " + sum + " " + currencyToSell + " to " + currencyToBuy);
			break;
		default:
			break;
		}

		return result + " sum: " + operationResult + ", used rate: " + rate.setScale(3, BigDecimal.ROUND_FLOOR);
	}

	private static void showHistory() throws CurrencyExchangeException {
		if (!history.isEmpty()) {
			System.out.println("_________________________");
			System.out.println("Show history? ==>>\n");
			System.out.println("Yes >> print 1");
			System.out.println("No  >> any other key");

			String cho = scan.next();
			int showHistory = Integer.parseInt(cho);

			if (showHistory == 1) {
				infoHistory();
			}
		} else {
			System.out.println("You have not made any operation");
		}
	}

	public static void infoHistory() {
		System.out.println("_________HISTORY_________");
		for (String h : history) {
			System.out.println(h.toString());
		}
		System.out.println("_________________________");
	}

	public static void infoCurrencyRate() {
		System.out.println("______CURRENCY RATE______");
		System.out.printf(" %6s  %11s\n", OperationName.BUY, OperationName.SELL);
		CurrencyRate.getInfoCurrencyRate(config.rates);
		System.out.println("_________________________");
	}
}