package com.burcuozel.statistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.burcuozel.statistics.exception.ExpiredTimeException;
import com.burcuozel.statistics.model.StatisticsResponse;
import com.burcuozel.statistics.model.Transaction;
import com.burcuozel.statistics.service.TransactionStatisticsService;



@Controller
public class TransactionStatisticsController {
	@Autowired
	private TransactionStatisticsService statisticService;

	@GetMapping("/statistics")
	public ResponseEntity<StatisticsResponse> getStatistics() {
		return new ResponseEntity<>(statisticService.getStatistics(), HttpStatus.OK);
	}

	@PostMapping("/transactions")
	public @ResponseBody ResponseEntity<Void> addTransaction(@RequestBody Transaction transaction) {
		try {
			statisticService.addTransaction(transaction);
		} catch (ExpiredTimeException ex) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@DeleteMapping("/transactions")
	public ResponseEntity<Void> deleteAllTransactions() {
		statisticService.deleteAllTransactions();

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
