package com.toy.namoner.common.exceptions;

import org.springframework.http.HttpStatus;

public class IllegalTokenException extends TraceErrorException {
	public IllegalTokenException(String message) {
		super(HttpStatus.BAD_REQUEST, message);
	}
}
