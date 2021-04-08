package com.burcuozel.statistics.configuration;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.burcuozel.statistics.model.Transaction;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class TransactionDeserializer extends JsonDeserializer<Transaction> {

	private static final DateTimeFormatter ISO_FIXED_FORMAT = DateTimeFormatter
			.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

	@Override
	public Transaction deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException {

		ObjectCodec oc = jsonParser.getCodec();
		JsonNode node = oc.readTree(jsonParser);

		BigDecimal amount = new BigDecimal(node.get("amount").textValue());

		LocalDateTime timeStamp = LocalDateTime.parse(node.get("timestamp").textValue(), ISO_FIXED_FORMAT);

		return new Transaction(amount, timeStamp);

	}
}