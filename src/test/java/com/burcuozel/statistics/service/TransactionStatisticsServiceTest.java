package com.burcuozel.statistics.service;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.burcuozel.statistics.Application;
import com.burcuozel.statistics.exception.ExpiredTimeException;
import com.burcuozel.statistics.model.StatisticsResponse;
import com.burcuozel.statistics.model.Transaction;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Application.class })
public class TransactionStatisticsServiceTest {

	@Inject
	private TransactionStatisticsService service;

	@Before
	public void init() {
		service.deleteAllTransactions();
	}

	@Test(expected = Test.None.class /* no exception expected */)
	public void testAddStatistics_withValidTransaction_added() {
		Transaction request = new Transaction(new BigDecimal(10), LocalDateTime.now(ZoneOffset.UTC));
		service.addTransaction(request);
	}

	@Test(expected = Test.None.class /* no exception expected */)
	public void testAddStatistics_withNegativeAmount_added() {
		Transaction request = new Transaction(new BigDecimal(-10), LocalDateTime.now(ZoneOffset.UTC));
		service.addTransaction(request);
	}

	@Test(expected = ExpiredTimeException.class)
	public void testAddStatistics_withInPastTimestampMoreThanAMinute_notAdded() {
		Transaction request = new Transaction(new BigDecimal(10), LocalDateTime.now(ZoneOffset.UTC).minusSeconds(100));
		service.addTransaction(request);
	}

	@Test(expected = InvalidParameterException.class)
	public void testAddStatistics_withInFutureTimestamp_notAdded() {
		Transaction request = new Transaction(new BigDecimal(10), LocalDateTime.now(ZoneOffset.UTC).plusSeconds(100));
		service.addTransaction(request);
	}

	@Test
	public void testGetStatistics_withAnyData_success() {

		StatisticsResponse response = service.getStatistics();
		Assert.assertEquals(0, response.getCount());
	}

	@Test
	public void testGetStatistics_withAData_success() throws Exception {

		Transaction request = new Transaction(new BigDecimal(-10), LocalDateTime.now(ZoneOffset.UTC));
		service.addTransaction(request);
		Thread.sleep(1000);
		StatisticsResponse response = service.getStatistics();
		Assert.assertEquals(1, response.getCount());
	}
}
