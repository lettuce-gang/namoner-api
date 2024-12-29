package com.toy.namoner.auth.controller.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String accessToken;
    private String refreshToken;

    private String phoneNum;
}
