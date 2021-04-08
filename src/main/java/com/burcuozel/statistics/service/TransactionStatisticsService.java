package com.burcuozel.statistics.service;

import com.burcuozel.statistics.model.StatisticsResponse;
import com.burcuozel.statistics.model.Transaction;

public interface TransactionStatisticsService {
	StatisticsResponse getStatistics();

	void addTransaction(Transaction transaction);

	void deleteAllTransactions();

}
