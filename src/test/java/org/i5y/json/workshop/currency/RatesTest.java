package org.i5y.json.workshop.currency;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonValue;

import junit.framework.TestCase;

import org.i5y.json.workshop.currency.Rates.RateNotPresentException;

public class RatesTest extends TestCase {

	public void testGBP20121201() {
		assertEquals(0.624385, Rates.getRate("GBP", "2012-12-01"));
	}

	public void testEUR20121202() {
		try {
			Rates.getRate("EUR", "2012-12-02");
			fail("Expected a RateNotPresentException");
		} catch (RateNotPresentException e) {
			// expected
		}
	}

	public void testfindBaseEquivalentCurrencies20121201() {
		Collection<String> expected = new HashSet<>(Arrays.asList("BMD", "BSD",
				"PAB"));
		assertEquals(expected,
				(Rates.findBaseEquivalentCurrencies("2012-12-01")));
	}

	public void testfindBaseEquivalentCurrencies20121202() {
		Collection<String> expected = new HashSet<>(Arrays.asList("BMD", "BSD",
				"PAB"));
		assertEquals(expected,
				(Rates.findBaseEquivalentCurrencies("2012-12-02")));
	}

	public void testGenerateReport() {
		Map<String, Double> expectedRates = new HashMap<>();
		expectedRates.put("Sat Dec 01 23:00:09 GMT 2012", 0.624385);
		expectedRates.put("Sun Dec 02 23:00:08 GMT 2012", 0.624168);
		expectedRates.put("Mon Dec 03 23:00:08 GMT 2012", 0.62174);
		expectedRates.put("Tue Dec 04 23:00:08 GMT 2012", 0.620952);
		expectedRates.put("Wed Dec 05 23:00:08 GMT 2012", 0.621273);
		expectedRates.put("Thu Dec 06 23:00:08 GMT 2012", 0.62268);
		expectedRates.put("Fri Dec 07 23:00:08 GMT 2012", 0.62287);
		JsonObject value = Rates.generateWeekReport("GBP", "2012-12-01");
		assertEquals("GBP", value.getStringValue("currency"));

		JsonArray rates = value.getValue("rates", JsonArray.class);
		assertEquals(7, rates.size());

		for (JsonValue jv : rates) {
			assertTrue(jv instanceof JsonObject);
			JsonObject jo = (JsonObject) jv;
			String timestamp = jo.getStringValue("timestamp");
			double rate = jo.getValue("rate", JsonNumber.class)
					.getDoubleValue();
			assertEquals(expectedRates.get(timestamp), rate);
		}
	}
}
