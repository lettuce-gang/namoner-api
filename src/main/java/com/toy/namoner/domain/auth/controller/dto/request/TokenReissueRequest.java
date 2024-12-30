package com.toy.namoner.domain.auth.controller.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TokenReissueRequest {
    private final String refreshToken;
}
