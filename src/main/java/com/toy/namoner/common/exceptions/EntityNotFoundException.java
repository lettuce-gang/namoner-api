package com.toy.namoner.common.exceptions;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends TraceErrorException {
	public EntityNotFoundException(String message) {
		super(HttpStatus.NOT_FOUND, message);
	}
}
