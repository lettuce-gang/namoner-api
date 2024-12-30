package com.toy.namoner.domain.auth.controller.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Builder
public class NMNTokenResponse {

    private final String accessToken;
    private final String refreshToken;
    private final LocalDateTime accessTokenExpiredTime;


    public static NMNTokenResponse create(String accessToken, String refreshToken, long accessTokenExpiration) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredTime = now.plusSeconds(accessTokenExpiration / 1000);

        return NMNTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiredTime(expiredTime)
                .build();
    }
}
