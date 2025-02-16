package com.toy.namoner.infra.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.toy.namoner.common.AppEnvironment;
import com.toy.namoner.common.error.exceptions.AuthorizationException;
import com.toy.namoner.infra.client.NaverProfileApiClient;
import com.toy.namoner.infra.client.NaverTokenApiClient;
import com.toy.namoner.infra.client.dto.response.NaverProfileApiResponse;
import com.toy.namoner.infra.client.dto.response.NaverTokenApiResponse;
import com.toy.namoner.domain.auth.controller.dto.request.NaverLoginRequest;
import com.toy.namoner.domain.auth.controller.dto.response.LoginResponse;
import com.toy.namoner.domain.auth.service.AuthService;
import com.toy.namoner.infra.service.OAuthService;
import com.toy.namoner.infra.service.dto.OAuthUserInfo;

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
			log.error("Naver Token API returns error \n error: {} \n error_description: {}", token.getError(),
				token.getError_description());

			throw new AuthorizationException("NAVER Token API returns error");
		}

		ResponseEntity<NaverProfileApiResponse> profileResponse = naverProfileApiClient.getProfile(
			token.getAuthenticationCode());
		NaverProfileApiResponse profile = profileResponse.getBody();

		OAuthUserInfo oAuthUserInfo = OAuthUserInfo.builder().phoneNum(profile.getPhoneNumber()).build();

		return authService.loginByOAuthInfo(oAuthUserInfo);
	}

}
