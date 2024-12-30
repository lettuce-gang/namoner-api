package com.toy.namoner.domain.auth.controller.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class LoginResponse {
    private final String accessToken;
    private final String refreshToken;

    private final Boolean isFirstVisit;

    public static LoginResponse createFirstLoginResponse(String accessToken, String refreshToken) {
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isFirstVisit(true)
                .build();
    }
    public static LoginResponse createLoginResponse(String accessToken, String refreshToken) {
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isFirstVisit(false)
                .build();
    }
}
