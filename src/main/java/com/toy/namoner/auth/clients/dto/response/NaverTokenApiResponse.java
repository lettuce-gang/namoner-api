package com.toy.namoner.auth.clients.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NaverTokenApiResponse {
//    @JsonProperty("access_token")
    private String access_token;
    private String refresh_token;
    private String token_type;
    private String expires_in;

    private String error;
    private String error_description;

    public boolean isError() {
        return error != null;
    }

    public String getAuthenticationCode() {
        return this.token_type + " " + this.access_token;
    }
}
