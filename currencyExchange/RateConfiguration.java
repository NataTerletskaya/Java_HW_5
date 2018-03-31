package currencyExchange;

import currencyExchange.enums.CurrencyName;
import currencyExchange.enums.OperationName;

public class RateConfiguration {
	// client sells or buys
	final static double USD_SELL = 26.25;
	final static double USD_BUY = 26.32;
	final static double EUR_SELL = 32.5;
	final static double EUR_BUY = 32.63;
	final static double RUB_SELL = 0.456;
	final static double RUB_BUY = 0.459;
	final static double UAH_SELL = 1;
	final static double UAH_BUY = 1;

	public double[][] rates = new double[CurrencyName.values().length][OperationName.values().length];

	// create a method to store a currency rate
	public RateConfiguration() {
		this.rates[CurrencyName.USD.ordinal()][OperationName.BUY.ordinal()] = USD_BUY;
		this.rates[CurrencyName.USD.ordinal()][OperationName.SELL.ordinal()] = USD_SELL;
		this.rates[CurrencyName.EUR.ordinal()][OperationName.BUY.ordinal()] = EUR_BUY;
		this.rates[CurrencyName.EUR.ordinal()][OperationName.SELL.ordinal()] = EUR_SELL;
		this.rates[CurrencyName.RUB.ordinal()][OperationName.BUY.ordinal()] = RUB_BUY;
		this.rates[CurrencyName.RUB.ordinal()][OperationName.SELL.ordinal()] = RUB_SELL;
		this.rates[CurrencyName.UAH.ordinal()][OperationName.BUY.ordinal()] = UAH_BUY;
		this.rates[CurrencyName.UAH.ordinal()][OperationName.SELL.ordinal()] = UAH_SELL;
	}
	

}
