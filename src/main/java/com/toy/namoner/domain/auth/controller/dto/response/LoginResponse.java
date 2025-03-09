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
    private final String postBoxName;

    public static LoginResponse createFirstLoginResponse(NMNToken token, User user) {
        return create(token, user, true);
    }
    public static LoginResponse createRegularLoginResponse(NMNToken token, User user) {
        return create(token, user, false);
    }

    private static LoginResponse create(NMNToken token, User user, boolean isFirstVisit) {
        return LoginResponse.builder()
                .token(token)
                .isFirstVisit(isFirstVisit)
                .userId(user.getId())
                .postBoxName(user.getPostboxName())
                .build();
    }

}
