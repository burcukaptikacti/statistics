package com.burcuozel.statistics.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpiredTimeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String errorMessage;

	public ExpiredTimeException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

}