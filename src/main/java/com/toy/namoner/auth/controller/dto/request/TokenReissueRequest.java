package com.toy.namoner.auth.controller.dto.request;

import lombok.Data;

@Data
public class TokenReissueRequest {
    private String refreshToken;
}
