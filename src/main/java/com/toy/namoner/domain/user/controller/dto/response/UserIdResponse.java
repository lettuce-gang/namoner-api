package com.toy.namoner.domain.user.controller.dto.response;

import com.toy.namoner.domain.user.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class UserIdResponse {
    private final String userId;

    public static UserIdResponse from(User user) {
        return UserIdResponse.builder()
                .userId(user.getId())
                .build();
    }

}
