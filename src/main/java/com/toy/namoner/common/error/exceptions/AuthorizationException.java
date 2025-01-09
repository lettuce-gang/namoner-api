package com.toy.namoner.common.error.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class AuthorizationException extends TraceErrorException {

	public AuthorizationException(String message) {
		super(HttpStatus.UNAUTHORIZED, message);
	}
}
