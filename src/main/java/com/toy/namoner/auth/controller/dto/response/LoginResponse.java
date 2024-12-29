package com.toy.namoner.auth.controller.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String accessToken;
    private String refreshToken;

    private Boolean isFirstVisit;

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
