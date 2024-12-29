package com.toy.namoner.auth.service;

import com.toy.namoner.auth.controller.dto.request.TokenReissueRequest;
import com.toy.namoner.auth.controller.dto.response.LoginResponse;
import com.toy.namoner.auth.controller.dto.response.NMNTokenResponse;
import com.toy.namoner.auth.service.dto.OAuthUserInfo;

public interface AuthService {

    LoginResponse loginByOAuthInfo(OAuthUserInfo info);
}
