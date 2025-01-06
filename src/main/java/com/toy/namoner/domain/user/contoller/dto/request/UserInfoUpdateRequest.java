package com.toy.namoner.domain.user.contoller.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class UserInfoUpdateRequest {
    private final String postBoxName;
    private final Boolean isPhoneConnected;
}
