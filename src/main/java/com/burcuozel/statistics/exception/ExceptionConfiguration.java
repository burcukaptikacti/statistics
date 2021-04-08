package com.burcuozel.statistics.exception;

import java.security.InvalidParameterException;
import java.time.DateTimeException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionConfiguration extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<Void> handleConverterErrors(NullPointerException exception) {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<Void> handleConverterErrors(NumberFormatException exception) {
		return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(DateTimeException.class)
	public ResponseEntity<Void> handleConverterErrors(DateTimeException exception) {
		return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(InvalidParameterException.class)
	public ResponseEntity<Void> handleConverterErrors(InvalidParameterException exception) {
		return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(ExpiredTimeException.class)
	public ResponseEntity<Void> handleConverterErrors(ExpiredTimeException exception) {
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}