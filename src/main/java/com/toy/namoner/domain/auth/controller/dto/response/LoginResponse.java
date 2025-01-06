package com.toy.namoner.domain.auth.controller.dto.response;

import com.toy.namoner.domain.user.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class LoginResponse {
    private final NMNToken token;
    private final Boolean isFirstVisit;
    private final String userId;

    public static LoginResponse createFirstLoginResponse(NMNToken token, User user) {
        return LoginResponse.builder()
                .token(token)
                .isFirstVisit(true)
                .userId(user.getId())
                .build();
    }
    public static LoginResponse createLoginResponse(NMNToken token, User user) {
        return LoginResponse.builder()
                .token(token)
                .isFirstVisit(false)
                .userId(user.getId())
                .build();
    }

}
