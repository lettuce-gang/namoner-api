package com.toy.namoner.domain.user.contoller.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class UserIdResponse {
    private final String userId;

    public static UserIdResponse from(String userId) {
        return UserIdResponse.builder()
                .userId(userId)
                .build();
    }

}
