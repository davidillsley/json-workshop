package org.i5y.json.workshop.currency;

import junit.framework.TestCase;

import org.i5y.json.workshop.currency.Rates.RateNotPresentException;

public class RatesTest extends TestCase {

	public void testGBP20121201() {
		assertEquals(0.624385, new Rates().getRate("GBP", "2012-12-01"));
	}

	public void testEUR20121202() {
		try {
			new Rates().getRate("EUR", "2012-12-02");
			fail("Expected a RateNotPresentException");
		} catch (RateNotPresentException e) {
			// expected
		}
	}
}
