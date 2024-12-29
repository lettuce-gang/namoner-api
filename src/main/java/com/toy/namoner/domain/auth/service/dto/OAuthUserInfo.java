package com.toy.namoner.domain.auth.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OAuthUserInfo {

    private String phoneNum;
}
