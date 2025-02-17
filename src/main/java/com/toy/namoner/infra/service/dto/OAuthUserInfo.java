package com.toy.namoner.infra.service.dto;

import com.toy.namoner.domain.user.model.enums.Gender;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class OAuthUserInfo {

    private final String phoneNum;
    private final Gender gender;
    private final String age;
}
