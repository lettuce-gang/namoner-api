package com.toy.namoner.domain.auth.controller.dto.request;

import lombok.Data;

@Data
public class TokenReissueRequest {
    private String refreshToken;
}
