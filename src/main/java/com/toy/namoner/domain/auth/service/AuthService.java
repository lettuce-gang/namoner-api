package com.toy.namoner.domain.auth.service;

import com.toy.namoner.domain.auth.controller.dto.response.LoginResponse;
import com.toy.namoner.domain.auth.service.dto.OAuthUserInfo;

public interface AuthService {

    LoginResponse loginByOAuthInfo(OAuthUserInfo info);
}
