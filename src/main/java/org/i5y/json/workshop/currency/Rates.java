package org.i5y.json.workshop.currency;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;

public class Rates {

	public static class RateNotPresentException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

	public double getRate(String currency, String date) {
		JsonObject obj = createJsonReader(date).readObject();
		return getRate(obj, currency);
	}

	private double getRate(JsonObject jsonDoc, String currency) {
		JsonObject rates = jsonDoc.getValue("rates", JsonObject.class);
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

	public Collection<String> findBaseEquivalentCurrencies(String date) {
		JsonObject obj = createJsonReader(date).readObject();
		JsonObject rates = obj.getValue("rates", JsonObject.class);
		Collection<String> result = new HashSet<>();
		String baseCurrency = obj.getStringValue("base", "USD");
		if (rates != null) {
			for (Entry<String, JsonValue> entry : rates.entrySet()) {
				if (entry.getValue() instanceof JsonNumber) {
					if (((JsonNumber) entry.getValue()).getDoubleValue() == 1.0) {
						if (!entry.getKey().equals(baseCurrency)) {
							result.add(entry.getKey());
						}
					}
				}
			}
		} else {
			throw new RateNotPresentException();
		}
		return result;
	}

	/**
	 * { currency : "USD", rates: [ {timestamp:"formatted", value: 1.x}, ...] }
	 * 
	 * @param currency
	 * @param date
	 * @return
	 */
	public JsonObject generateWeekReport(String currency) {
		List<String> dates = Arrays.asList("2012-12-01", "2012-12-02",
				"2012-12-03", "2012-12-04", "2012-12-05", "2012-12-06",
				"2012-12-07");
		JsonArrayBuilder ratesArrayBuilder = Json.createArrayBuilder();

		for (String date : dates) {
			JsonObject obj = createJsonReader(date).readObject();
			JsonNumber jn = obj.getValue("timestamp", JsonNumber.class);
			long time = jn.getLongValue();
			double rate = getRate(obj, currency);
			ratesArrayBuilder.add(Json.createObjectBuilder()
					.add("timestamp", new Date(time * 1000l).toString())
					.add("rate", rate));
		}

		JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
				.add("currency", currency).add("rates", ratesArrayBuilder);

		return objectBuilder.build();
	}

	private JsonReader createJsonReader(String date) {
		InputStream is = getClass().getResourceAsStream("/" + date + ".json");
		return Json.createReader(is);
	}
}
