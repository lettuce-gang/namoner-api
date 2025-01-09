package com.toy.namoner.domain.auth.service.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.toy.namoner.common.AppEnvironment;
import com.toy.namoner.common.exceptions.AuthorizationException;
import com.toy.namoner.domain.auth.clients.NaverProfileApiClient;
import com.toy.namoner.domain.auth.clients.NaverTokenApiClient;
import com.toy.namoner.domain.auth.clients.dto.response.NaverProfileApiResponse;
import com.toy.namoner.domain.auth.clients.dto.response.NaverTokenApiResponse;
import com.toy.namoner.domain.auth.controller.dto.request.NaverLoginRequest;
import com.toy.namoner.domain.auth.controller.dto.response.LoginResponse;
import com.toy.namoner.domain.auth.service.AuthService;
import com.toy.namoner.domain.auth.service.dto.OAuthUserInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NaverOAuthService implements OAuthService {
    private static final String GRANT_TYPE = "authorization_code";

    private final NaverTokenApiClient naverTokenApiClient;
    private final NaverProfileApiClient naverProfileApiClient;
    private final AuthService authService;
    private final AppEnvironment env;

    @Override
    public LoginResponse getUserInfo(NaverLoginRequest request) {

        ResponseEntity<NaverTokenApiResponse> tokenResponse = naverTokenApiClient.getToken(GRANT_TYPE,
            env.getClientId(), env.getClientSecret(), request.getCode(), request.getState());
        NaverTokenApiResponse token = tokenResponse.getBody();

        if (token.isError()) {
            log.error("Naver Token API returns error \n error: {} \n error_description: {}", token.getError(), token.getError_description());

            throw new AuthorizationException("NAVER Token API returns error");
        }

        ResponseEntity<NaverProfileApiResponse> profileResponse = naverProfileApiClient.getProfile(token.getAuthenticationCode());
        NaverProfileApiResponse profile = profileResponse.getBody();

        OAuthUserInfo oAuthUserInfo = OAuthUserInfo.builder().phoneNum(profile.getPhoneNumber()).build();

        return authService.loginByOAuthInfo(oAuthUserInfo);
    }

}
