package com.burcuozel.statistics.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.burcuozel.statistics.configuration.TransactionDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonDeserialize(using = TransactionDeserializer.class)
public class Transaction {

	private BigDecimal amount;
	private LocalDateTime timestamp;

}
