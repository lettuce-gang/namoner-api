package com.toy.namoner.common.error.exceptions;

import org.springframework.http.HttpStatus;

public class CannotReadableLetterException extends TraceErrorException {
	public CannotReadableLetterException(String message) {
		super(HttpStatus.BAD_REQUEST, message);
	}
}
