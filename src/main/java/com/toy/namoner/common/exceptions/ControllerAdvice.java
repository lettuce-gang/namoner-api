package com.toy.namoner.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.toy.namoner.common.model.Response;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

	@ExceptionHandler(Throwable.class)
	public Response<Void> exceptionHandler(Throwable throwable, HttpServletRequest request) {
		TraceErrorException exception;

		if (throwable instanceof TraceErrorException) {
			exception = (TraceErrorException)throwable;
		} else {
			exception = new TraceErrorException(HttpStatus.INTERNAL_SERVER_ERROR, throwable);
		}

		log.error("[{}] Error occurred while processing request. method: {}, uri: {}", exception.getTraceCode(),
			request.getMethod(), request.getRequestURI(), exception);

		return Response.error(exception);
	}
}
