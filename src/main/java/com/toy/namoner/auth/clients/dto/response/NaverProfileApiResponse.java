package com.toy.namoner.auth.clients.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
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
