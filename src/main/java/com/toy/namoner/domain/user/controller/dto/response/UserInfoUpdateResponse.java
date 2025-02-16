package com.toy.namoner.domain.user.controller.dto.response;

import com.toy.namoner.domain.user.model.User;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class UserInfoUpdateResponse {
    private final String message;
    public static UserInfoUpdateResponse from(User user) {
        return UserInfoUpdateResponse.builder()
                .message("update Success")
                .build();
    }
    
}
