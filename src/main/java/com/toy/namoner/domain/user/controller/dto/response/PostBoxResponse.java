package com.toy.namoner.domain.user.controller.dto.response;

import com.toy.namoner.domain.user.model.User;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class PostBoxResponse {
    private final Boolean isOwner;
    private final Boolean existPostBox;
    private final String postboxName;
    private final Integer unreadLetterCount;

    public static PostBoxResponse createNonOwnerPostBox(User user) {
        return createUserPostBox(user, false);
    }

    public static PostBoxResponse createGuestPostBox() {
        return PostBoxResponse.builder()
                .existPostBox(false)
                .build();
    }

    public static PostBoxResponse createOwnerPostBox(User user) {
        return createUserPostBox(user, true);
    }

    private static PostBoxResponse createUserPostBox(User user, boolean isOwner) {
        return PostBoxResponse.builder()
                .isOwner(isOwner)
                .existPostBox(true)
                .postboxName(user.getPostboxName())
                .unreadLetterCount(user.getUnreadLetterCount())
                .build();
    }
}
