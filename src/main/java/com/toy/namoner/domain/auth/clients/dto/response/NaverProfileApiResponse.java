package com.toy.namoner.domain.auth.clients.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class NaverProfileApiResponse {

    @JsonProperty("resultcode")
    private String resultCode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("response")
    private NaverProfileUserInfoResponse response;

    public String getPhoneNumber() {
        return response.getMobile();
    }

}
