package com.toy.namoner.common.model;

import java.time.Instant;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.toy.namoner.common.exceptions.TraceErrorException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
	private final boolean success;
	private final Instant timestamp;
	private final String errorTraceCode;
	private final String message;
	private final T data;

	public static <T> Response<T> success(T data) {
		return new Response<>(true, Instant.now(), null, null, data);
	}

	public static <T> Response<T> error(TraceErrorException error) {
		return new Response<>(false, Instant.now(), error.getTraceCode(),
			ExceptionUtils.getRootCauseMessage(error), null);
	}

	public static <T> Response<T> error(Throwable error) {
		return new Response<>(false, Instant.now(), null,
			ExceptionUtils.getRootCauseMessage(error), null);
	}
}
