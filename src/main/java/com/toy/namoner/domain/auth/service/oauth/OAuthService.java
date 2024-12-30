package com.toy.namoner.domain.auth.service.oauth;

import com.toy.namoner.domain.auth.controller.dto.request.NaverLoginRequest;
import com.toy.namoner.domain.auth.controller.dto.response.LoginResponse;

public interface OAuthService {

    LoginResponse getUserInfo(NaverLoginRequest request);
}
