package com.toy.namoner.domain.auth.service.oauth;

import com.toy.namoner.domain.auth.clients.NaverProfileApiClient;
import com.toy.namoner.domain.auth.clients.NaverTokenApiClient;
import com.toy.namoner.domain.auth.clients.dto.response.NaverProfileApiResponse;
import com.toy.namoner.domain.auth.clients.dto.response.NaverTokenApiResponse;
import com.toy.namoner.domain.auth.controller.dto.request.NaverLoginRequest;
import com.toy.namoner.domain.auth.controller.dto.response.LoginResponse;
import com.toy.namoner.domain.auth.service.AuthService;
import com.toy.namoner.domain.auth.service.dto.OAuthUserInfo;
import com.toy.namoner.common.exceptions.AuthorizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NaverOAuthService implements OAuthService {
    private final NaverTokenApiClient naverTokenApiClient;

    private final NaverProfileApiClient naverProfileApiClient;
    private final AuthService authService;

    private final String GRANT_TYPE = "authorization_code";
    @Value("${oauth.naver.client.id}")
    private String clientId;
    @Value("${oauth.naver.client.secret}")
    private String clientSecret;

    @Override
    public LoginResponse getUserInfo(NaverLoginRequest request) {

        ResponseEntity<NaverTokenApiResponse> tokenResponse = naverTokenApiClient.getToken(GRANT_TYPE, clientId, clientSecret, request.getCode(), request.getState());
        NaverTokenApiResponse token = tokenResponse.getBody();

        if (token.isError()) {
            throw new AuthorizationException("NAVER Token API returns error");
        }

        ResponseEntity<NaverProfileApiResponse> profileResponse = naverProfileApiClient.getProfile(token.getAuthenticationCode());
        NaverProfileApiResponse profile = profileResponse.getBody();

        OAuthUserInfo oAuthUserInfo = OAuthUserInfo.builder().phoneNum(profile.getPhoneNumber()).build();

        return authService.loginByOAuthInfo(oAuthUserInfo);
    }

}
