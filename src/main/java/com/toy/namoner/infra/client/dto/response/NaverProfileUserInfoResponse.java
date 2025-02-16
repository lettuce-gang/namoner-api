package com.toy.namoner.infra.client.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NaverProfileUserInfoResponse {

    private final String id;

    private final String mobile;

    @JsonProperty("mobile_e164")
    private final String mobileE164;
}
