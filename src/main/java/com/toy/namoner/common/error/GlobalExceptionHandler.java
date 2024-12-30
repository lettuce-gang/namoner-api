package com.toy.namoner.common.error;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.toy.namoner.common.error.exceptions.TraceErrorException;
import com.toy.namoner.common.model.Response;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<Response<Void>> exceptionHandler(Throwable throwable, HttpServletRequest request) {
		TraceErrorException exception;

		if (throwable instanceof TraceErrorException) {
			exception = (TraceErrorException)throwable;
		} else {
			exception = new TraceErrorException(HttpStatus.INTERNAL_SERVER_ERROR, throwable);
		}

		HttpStatus statusCode = ObjectUtils.defaultIfNull(exception.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
		log.error("[{}] Error occurred while processing request. statusCode: {}, uri: {}", exception.getTraceCode(),
			statusCode, request.getRequestURI(), exception);

		return ResponseEntity.status(statusCode)
			.body(Response.error(exception));
	}
}
