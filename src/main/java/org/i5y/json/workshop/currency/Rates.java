package org.i5y.json.workshop.currency;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.json.JsonObject;
import javax.json.JsonReader;

public class Rates {

	public static class RateNotPresentException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

	/**
	 * Get an exchange rate from USD to the provided currency on a certain date
	 * 
	 * @param currency
	 * @param date
	 *            formatted e.g. 2012-12-01
	 * @return
	 * @throws RateNotPresentException
	 *             if the rate is not available for the currency/date pair
	 */
	public static double getRate(String currency, String date) {
		JsonObject obj = createJsonReader(date).readObject();
		return getRate(obj, currency);
	}

	private static double getRate(JsonObject jsonDoc, String currency) {
		// TODO
		return 0;
	}

	/**
	 * Find currencies which have an exchange rate of 1 on a given date
	 * 
	 * @param date
	 * @return
	 *  * @throws RateNotPresentException
	 *             if the rates are not available for the given date
	 */
	public static Collection<String> findBaseEquivalentCurrencies(String date) {
		return null;
	}

	/**
	 * Generates a JSON report for a specific currency for a week from the
	 * provided date in the format:
	 * 
	 * { currency : "USD", rates: [ { timestamp :"Sat Feb 01 23:00:09 GMT 2013",
	 * value: 1.x}, ...] }
	 * 
	 * @param currency
	 * @param date
	 * @return
	 */
	public static JsonObject generateWeekReport(String currency,
			String startDate) {
		String[] segments = startDate.split("-");
		int day = Integer.parseInt(segments[2]);

		List<String> dates = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			String dayStr = Integer.toString(day + i);
			if (dayStr.length() == 1)
				dayStr = "0" + dayStr;
			dates.add(segments[0] + "-" + segments[1] + "-" + dayStr);
		}

		// TODO
		return null;
	}

	/**
	 * Return a JsonReader object which will read the document for the
	 * appropriate date
	 * 
	 * @param date
	 * @return
	 */
	private static JsonReader createJsonReader(String date) {
		InputStream is = Rates.class.getResourceAsStream("/" + date + ".json");
		// TODO
		return null;
	}
}
