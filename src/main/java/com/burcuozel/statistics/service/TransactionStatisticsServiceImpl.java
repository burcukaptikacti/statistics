package com.burcuozel.statistics.service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.burcuozel.statistics.exception.ExpiredTimeException;
import com.burcuozel.statistics.model.Statistics;
import com.burcuozel.statistics.model.StatisticsResponse;
import com.burcuozel.statistics.model.Transaction;

@Service
public class TransactionStatisticsServiceImpl implements TransactionStatisticsService {

	private static final int SECONDS_PER_MINUTES = 59;

	Map<LocalDateTime, Statistics> statisticsRepo = new ConcurrentHashMap<>();

	@Override
	public StatisticsResponse getStatistics() {

		LocalDateTime startDateTime = LocalDateTime.now(ZoneOffset.UTC).minusSeconds(SECONDS_PER_MINUTES).withNano(0);
		Statistics statistics = new Statistics();

		for (int i = 0; i <= SECONDS_PER_MINUTES; i++) {
			statisticsRepo.computeIfPresent(startDateTime, (key, value) -> {
				statistics.combine(value);
				return value;
			});

			startDateTime = startDateTime.plusSeconds(1);
		}

		return new StatisticsResponse(statistics);
	}

	@Override
	public void addTransaction(Transaction transaction) {

		validateTransaction(transaction);

		LocalDateTime transactionTime = transaction.getTimestamp().withNano(0);

		statisticsRepo.putIfAbsent(transactionTime, new Statistics());

		statisticsRepo.compute(transactionTime, (key, statistics) -> {
			statistics.accept(transaction.getAmount());
			return statistics;
		});
	}

	private void validateTransaction(Transaction transaction) {
		if (transaction.getTimestamp().isBefore(LocalDateTime.now(ZoneOffset.UTC).minusSeconds(60))) {
			throw new ExpiredTimeException("The transaction is older than 60 seconds");
		}

		if (transaction.getTimestamp().isAfter(LocalDateTime.now(ZoneOffset.UTC))) {
			throw new InvalidParameterException("The transaction date is in the future");
		}
	}

	@Override
	public void deleteAllTransactions() {
		statisticsRepo.clear();
	}

}
