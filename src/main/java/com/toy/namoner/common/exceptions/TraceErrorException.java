package com.toy.namoner.common.exceptions;

import java.util.UUID;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class TraceErrorException extends RuntimeException {
	private final String traceCode;
	private final HttpStatus statusCode;

	public TraceErrorException(HttpStatus statusCode) {
		this(statusCode, "");
	}

	public TraceErrorException(HttpStatus statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
		this.traceCode = generateTraceCode();
	}

	public TraceErrorException(HttpStatus statusCode, Throwable cause) {
		super(cause);
		this.statusCode = statusCode;
		this.traceCode = generateTraceCode();
	}

	private static String generateTraceCode() {
		return UUID.randomUUID().toString();
	}
}
