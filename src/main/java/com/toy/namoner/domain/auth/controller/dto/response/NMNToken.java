package com.toy.namoner.domain.auth.controller.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class NMNToken {

    private final String accessToken;
    private final String refreshToken;
    private final LocalDateTime accessTokenExpiredTime;


    public static NMNToken create(String accessToken, String refreshToken, LocalDateTime accessTokenExpiredTime) {

        return NMNToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiredTime(accessTokenExpiredTime)
                .build();
    }
}
