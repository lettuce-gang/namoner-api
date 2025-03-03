package com.toy.namoner.domain.user.controller.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class UserJoinResponse {
    private final String message;
    public static UserJoinResponse from() {
        return UserJoinResponse.builder()
                .message("update Success")
                .build();
    }

}
