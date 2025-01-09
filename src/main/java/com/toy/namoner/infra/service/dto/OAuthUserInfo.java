package com.toy.namoner.infra.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class OAuthUserInfo {

    private final String phoneNum;
}
