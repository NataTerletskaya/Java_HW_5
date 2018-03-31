package currencyExchange;

import currencyExchange.enums.CurrencyName;
import currencyExchange.enums.OperationName;

public class CurrencyRate {

	double[][] rates; // = new double[CurrencyName.values().length][OperationName.values().length];
	CurrencyName baseCurrency;

	public CurrencyRate(double[][] rates, CurrencyName baseCurrency) {
		this.rates = rates;
		this.baseCurrency = baseCurrency;
	}

	public double getSell(CurrencyName currency) {
		return getRate(currency, OperationName.SELL);
	}

	public double getBuy(CurrencyName currency) {
		return getRate(currency, OperationName.BUY);
	}

	// calculation of cross course currencyWeHave/currencyWeWant
	public double getRate(CurrencyName from, CurrencyName to) {
		if (to == this.baseCurrency) {
			return getSell(from);
		}
		if (from == this.baseCurrency) {
			return getBuy(to);
		}

		double sellRate = getSell(from);
		double buyRate = getBuy(to);
		double result = sellRate / buyRate;
		infoCourse(from, to, result);
		return result;
	}

	private void infoCourse(CurrencyName from, CurrencyName to, double result) {
		System.out.println("cross currency rate is " + Math.floor(result * 1000) / 1000.0);
		System.out.println("course for " + from.toString() + " is " + getSell(from));
		System.out.println("course for " + to.toString() + " is " + getBuy(to));
	}

	/*
	 * method returning an Array with currency rate (is taken from
	 * RateConfiguretion)
	 */
	private double getRate(CurrencyName currency, OperationName operation) {
		return rates[currency.ordinal()][operation.ordinal()];
	}

	public static String getOperationList() {
		String res = "";

		for (int i = 0; i < OperationName.values().length; i++) {
			res += OperationName.values()[i].toString();

			if (i < OperationName.values().length - 1) {
				res += ", ";
			}
		}
		return res;
	}

	public static String getCurrencyNameList() {
		String res = "";

		for (int i = 0; i < CurrencyName.values().length; i++) {
			res += CurrencyName.values()[i].toString();

			if (i < CurrencyName.values().length - 1) {
				res += ", ";
			}
		}

		return res;
	}

	public static void getInfoCurrencyRate(double[][] arr) {
		int n = arr.length;
		int m = arr[0].length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				System.out.printf(" %2s  %7.3f", CurrencyName.values()[i].toString(), arr[i][j]);
			}
			System.out.println();
		}
	}

}
