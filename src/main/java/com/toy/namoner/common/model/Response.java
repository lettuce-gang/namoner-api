package com.toy.namoner.common.model;

import java.time.Instant;

import com.toy.namoner.common.exceptions.TraceErrorException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Response<T> {
	private final boolean success;
	private final Instant timestamp;
	private final String errorTraceCode;
	private final Throwable error;
	private final T data;

	public static <T> Response<T> success(T data) {
		return new Response<>(true, Instant.now(), null, null, data);
	}

	public static <T> Response<T> error(TraceErrorException error) {
		return new Response<>(false, Instant.now(), error.getTraceCode(), error, null);
	}

	public static <T> Response<T> error(Throwable error) {
		return new Response<>(false, Instant.now(), null, error, null);
	}
}
