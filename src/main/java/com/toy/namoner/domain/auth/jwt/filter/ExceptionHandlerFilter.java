package com.toy.namoner.domain.auth.jwt.filter;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.toy.namoner.common.exceptions.AuthorizationException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AuthorizationException e) {
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
        }
    }
}
