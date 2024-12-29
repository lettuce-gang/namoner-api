package com.toy.namoner.domain.user.contoller.dto.response;

import com.toy.namoner.domain.user.model.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostBoxResponse {
    private String postboxName;

    public static PostBoxResponse from(User user) {
        return PostBoxResponse.builder()
                .postboxName(user.getPostboxName())
                .build();
    }
}
