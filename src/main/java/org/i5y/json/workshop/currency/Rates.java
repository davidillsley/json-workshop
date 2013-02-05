package org.i5y.json.workshop.currency;

import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

public class Rates {

	public static class RateNotPresentException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

	public double getRate(String currency, String date) {
		JsonObject obj = createJsonReader(date).readObject();
		JsonObject rates = obj.getValue("rates", JsonObject.class);
		if (rates != null) {
			JsonValue jn = rates.get(currency);
			if (jn instanceof JsonNumber) {
				return ((JsonNumber) jn).getDoubleValue();
			} else {
				throw new RateNotPresentException();
			}
		} else {
			throw new RateNotPresentException();
		}
	}

	private JsonReader createJsonReader(String date) {
		InputStream is = getClass().getResourceAsStream("/" + date + ".json");
		return Json.createReader(is);
	}
}
