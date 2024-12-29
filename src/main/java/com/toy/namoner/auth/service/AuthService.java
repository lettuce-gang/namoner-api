package com.toy.namoner.auth.service;

import com.toy.namoner.auth.controller.dto.request.NaverLoginRequest;
import com.toy.namoner.auth.controller.dto.response.LoginResponse;

public interface AuthService {

    LoginResponse login(NaverLoginRequest request);
}
