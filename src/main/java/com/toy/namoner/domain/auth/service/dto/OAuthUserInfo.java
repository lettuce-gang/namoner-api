package com.toy.namoner.domain.auth.service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class OAuthUserInfo {

    private final String phoneNum;
}
