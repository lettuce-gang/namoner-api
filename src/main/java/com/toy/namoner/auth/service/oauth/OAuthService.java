package com.toy.namoner.auth.service.oauth;

import com.toy.namoner.auth.controller.dto.request.NaverLoginRequest;
import com.toy.namoner.auth.controller.dto.response.LoginResponse;
import com.toy.namoner.auth.service.dto.OAuthUserInfo;

public interface OAuthService {

    LoginResponse getUserInfo(NaverLoginRequest request);
}
