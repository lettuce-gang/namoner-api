package com.toy.namoner.common.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthorizationException extends TraceErrorException {

    public AuthorizationException() {
        super(HttpStatus.UNAUTHORIZED);
    }
    public AuthorizationException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
