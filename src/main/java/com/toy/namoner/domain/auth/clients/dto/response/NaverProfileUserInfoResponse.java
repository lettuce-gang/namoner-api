package com.toy.namoner.domain.auth.clients.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NaverProfileUserInfoResponse {

    private String id;

    private String mobile;

    @JsonProperty("mobile_e164")
    private String mobileE164;
}
