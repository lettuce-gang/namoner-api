package com.toy.namoner.domain.user.controller.dto.response;

import com.toy.namoner.domain.user.model.User;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class PostBoxResponse {
    private final Boolean existPostBox;
    private final String postboxName;
    private final Integer unreadLetterCount;

    public static PostBoxResponse from(User user) {
        return PostBoxResponse.builder()
                .existPostBox(true)
                .postboxName(user.getPostboxName())
                .unreadLetterCount(user.getUnreadLetterCount())
                .build();
    }

    public static PostBoxResponse createNotSignedUserPostBox() {
        return PostBoxResponse.builder()
                .existPostBox(false)
                .build();
    }

}
