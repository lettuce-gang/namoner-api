package com.toy.namoner.domain.user.contoller.dto.response;

import com.toy.namoner.domain.user.model.User;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class PostBoxResponse {
    private final String postboxName;

    public static PostBoxResponse from(User user) {
        return PostBoxResponse.builder()
                .postboxName(user.getPostboxName())
                .build();
    }
}
