package com.toy.namoner.common.error.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotAllowedException extends TraceErrorException {
	public UserNotAllowedException() {
		super(HttpStatus.BAD_REQUEST);
	}
	public UserNotAllowedException(String message) {
		super(HttpStatus.BAD_REQUEST, message);
	}
}
