package com.toy.namoner.common.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class AuthorizationException extends TraceErrorException {

    public AuthorizationException() {
        super(HttpStatus.UNAUTHORIZED);
    }
    public AuthorizationException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
