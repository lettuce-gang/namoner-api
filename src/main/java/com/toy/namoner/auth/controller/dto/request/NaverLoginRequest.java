package com.toy.namoner.auth.controller.dto.request;

import lombok.Data;

@Data
public class NaverLoginRequest {
    private String code;
    private String state;



}
