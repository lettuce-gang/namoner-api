package com.toy.namoner.auth.controller.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NMNTokenResponse {

    private String accessToken;
    private String refreshToken;
    private LocalDateTime accessTokenExpiredTime;


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
