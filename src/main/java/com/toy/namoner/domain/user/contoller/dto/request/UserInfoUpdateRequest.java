package com.toy.namoner.domain.user.contoller.dto.request;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@RequiredArgsConstructor
@Builder
public class UserInfoUpdateRequest {
    private final String postBoxName;
    private final Boolean isPhoneConnected;
}
