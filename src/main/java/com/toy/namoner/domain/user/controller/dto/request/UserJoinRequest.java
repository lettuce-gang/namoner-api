package com.toy.namoner.domain.user.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class UserJoinRequest {
    @NotEmpty
    private final String postBoxName;
    private final Boolean isPhoneConnected;
    private final String referrer;
}
