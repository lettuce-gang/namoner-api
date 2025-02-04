package com.toy.namoner.common.error.exceptions;

import org.springframework.http.HttpStatus;

public class UserSenderEmptyException extends TraceErrorException {
	public UserSenderEmptyException() {
		super(HttpStatus.BAD_REQUEST);
	}
}
