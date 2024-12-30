package com.toy.namoner.domain.auth.clients.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NaverTokenApiResponse {
    private final String access_token;
    private final String refresh_token;
    private final String token_type;
    private final String expires_in;

    private final String error;
    private final String error_description;

    public boolean isError() {
        return error != null;
    }

    public String getAuthenticationCode() {
        return this.token_type + " " + this.access_token;
    }
}
