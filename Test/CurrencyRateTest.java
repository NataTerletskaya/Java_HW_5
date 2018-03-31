package Test;
import currencyExchange.CurrencyName;
import currencyExchange.CurrencyRate;


public class CurrencyRateTest {

	public static void main(String[] args) {
		double[][] rates = new double[4][2];
		rates[0][0] = 25;
		rates[0][1] = 26;
		rates[1][0] = 32;
		rates[1][1] = 35;
		rates[2][0] = 0.5;
		rates[2][1] = 0.6;
		rates[3][0] = 1;
		rates[3][1] = 1;

		CurrencyRate r = new CurrencyRate(rates);
		// usd to uah
		System.out.println("Test rate usd -> uah");
		double expected = 25;
		double actual = r.getRate(CurrencyName.USD, CurrencyName.UAH);
		assertEqualDouble(expected, actual);
		// uah to usd
		System.out.println("Test rate uah -> usd");
		expected = 26;
		actual = r.getRate(CurrencyName.UAH, CurrencyName.USD);
		assertEqualDouble(expected, actual);
		// usd to eur
		System.out.println("Test rate usd -> eur");
		expected = 0.71;
		actual = r.getRate(CurrencyName.USD, CurrencyName.EUR);
		actual = Math.floor(actual * 100) / 100.0;
		assertEqualDouble(expected, actual);
	}

	protected static void assertEqualDouble(double expected, double actual) {
		if (expected != actual) {
			String message = "Test is failed!";
			message += "\nExpected: " + expected;
			message += "\nActual: " + actual;
			throw new Error(message);
		}
		System.out.println("Test is passed");
	}
}
