package com.burcuozel.statistics.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.burcuozel.statistics.Application;
import com.burcuozel.statistics.model.StatisticsResponse;
import com.burcuozel.statistics.model.Transaction;
import com.burcuozel.statistics.service.TransactionStatisticsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Application.class })
public class TransactionStatisticsControllerTest {

	@Inject
	private TransactionStatisticsController controller;

	@Inject
	private TransactionStatisticsService service;

	@Before
	public void init() {
		service.deleteAllTransactions();
	}

	@Test
	public void testAddStatistics_withValidTransaction() {
		Transaction transaction = new Transaction(new BigDecimal(10), LocalDateTime.now(ZoneOffset.UTC));
		ResponseEntity<Void> responseEntity = controller.addTransaction(transaction);
		Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}

	@Test
	public void testAddStatistics_withValidTransactionNegativeAmount() {
		Transaction transaction = new Transaction(new BigDecimal(-10), LocalDateTime.now(ZoneOffset.UTC));
		ResponseEntity<Void> responseEntity = controller.addTransaction(transaction);
		Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}

	@Test
	public void testAddStatistics_withOldTransaction() {
		Transaction transaction = new Transaction(new BigDecimal(10), LocalDateTime.now(ZoneOffset.UTC).minusSeconds(100));
		ResponseEntity<Void> responseEntity = controller.addTransaction(transaction);
		Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	}

	@Test
	public void testAddAndGetStatistics_withInValidTimestampWithinAMinuteWithSameTime_success() throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		int n = 0;
		double amount = 1.0;
		int count = 50000;
		while (n < count) {
			Transaction transaction = new Transaction(new BigDecimal(amount), LocalDateTime.now(ZoneOffset.UTC));
			executorService.submit(() -> controller.addTransaction(transaction));
			n++;
			amount += 1;
		}

		executorService.shutdown();
		Thread.sleep(1000);
		ResponseEntity<StatisticsResponse> response = controller.getStatistics();
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals(count, (response.getBody()).getCount());
	}

}
