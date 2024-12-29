package com.toy.namoner.auth.service;

import com.toy.namoner.auth.clients.NaverProfileApiClient;
import com.toy.namoner.auth.clients.NaverTokenApiClient;
import com.toy.namoner.auth.clients.dto.response.NaverProfileApiResponse;
import com.toy.namoner.auth.clients.dto.response.NaverTokenApiResponse;
import com.toy.namoner.auth.controller.dto.request.NaverLoginRequest;
import com.toy.namoner.auth.controller.dto.response.LoginResponse;
import com.toy.namoner.common.exceptions.CommonBadRequestException;
import com.toy.namoner.common.exceptions.CommonResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NaverAuthService implements AuthService {
    private final NaverTokenApiClient naverTokenApiClient;

    private final NaverProfileApiClient naverProfileApiClient;

    private final String GRANT_TYPE = "authorization_code";
    @Value("${oauth.naver.client.id}")
    private String clientId;
    @Value("${oauth.naver.client.secret}")
    private String clientSecret;

    @Override
    public LoginResponse login(NaverLoginRequest request) {

        ResponseEntity<NaverTokenApiResponse> tokenResponse = naverTokenApiClient.getToken(GRANT_TYPE, clientId, clientSecret, request.getCode(), request.getState());
        NaverTokenApiResponse token = tokenResponse.getBody();

        if (token.isError()) {
            throw new CommonBadRequestException(CommonResponseCode.COMMON_BAD_REQUEST, "NAVER Get token error");
        }

        ResponseEntity<NaverProfileApiResponse> profileResponse = naverProfileApiClient.getProfile(token.getAuthenticationCode());
        NaverProfileApiResponse profile = profileResponse.getBody();

        return LoginResponse.builder()
                .phoneNum(profile.getPhoneNumber())
                .build();
    }

}
