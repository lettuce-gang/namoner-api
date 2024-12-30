package com.toy.namoner.domain.auth.clients.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
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
