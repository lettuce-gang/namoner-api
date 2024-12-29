package com.toy.namoner.common.error.exceptions;

import org.springframework.http.HttpStatus;

public class IllegalLetterTypeException extends TraceErrorException {
	public IllegalLetterTypeException() {
		super(HttpStatus.BAD_REQUEST);
	}
}
