package com.toy.namoner.domain.auth.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NaverLoginRequest {
    private final String code;
    private final String state;

}
