package com.toy.namoner.common.error.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyJoinException extends TraceErrorException {
	public UserAlreadyJoinException() {
		super(HttpStatus.BAD_REQUEST);
	}
	public UserAlreadyJoinException(String message) {
		super(HttpStatus.BAD_REQUEST, message);
	}
}
