package com.toy.namoner.common.jwt.filter;

import com.toy.namoner.common.jwt.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String accessToken = jwtService.resolveAccessTokenFromHeader(request);

        if (jwtService.validateAccessToken(accessToken)) {
            setAuthenticationToContext(accessToken);
            filterChain.doFilter(request, response);
            return;
        }

        setAnonymousAuthenticationToContext();
        filterChain.doFilter(request, response);
    }

    private void setAuthenticationToContext(String accessToken) {
        Authentication authentication = jwtService.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    private void setAnonymousAuthenticationToContext() {
        SecurityContextHolder.getContext().setAuthentication(jwtService.getAnonymousAuthentication());
    }
}
