package com.toy.namoner.common.jwt.filter;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.namoner.common.error.GlobalExceptionHandler;
import com.toy.namoner.common.model.Response;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

	private final GlobalExceptionHandler exceptionHandler;
	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (Throwable t) {
			ResponseEntity<Response<Void>> entity = exceptionHandler.handleThrowable(t, request);
			String bodyJson = objectMapper.writeValueAsString(entity.getBody());

			response.setStatus(entity.getStatusCode().value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().write(bodyJson);
			response.flushBuffer();
		}
	}
}
