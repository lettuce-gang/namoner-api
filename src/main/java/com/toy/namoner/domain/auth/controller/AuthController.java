package com.toy.namoner.domain.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toy.namoner.common.handler.NamonerResponse;
import com.toy.namoner.domain.auth.controller.dto.request.NaverLoginRequest;
import com.toy.namoner.domain.auth.controller.dto.request.TokenReissueRequest;
import com.toy.namoner.domain.auth.controller.dto.response.LoginResponse;
import com.toy.namoner.domain.auth.controller.dto.response.NMNToken;
import com.toy.namoner.domain.auth.jwt.JwtService;
import com.toy.namoner.domain.auth.service.oauth.OAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final OAuthService naverOAuthService;
	private final JwtService jwtService;

	/**
	 * 토큰 재발급
	 *
	 * @param request 토큰 재발급 요청
	 * @return 재발급된 토큰
	 */
	@NamonerResponse
	@PostMapping("/reissue")
	public NMNToken reissueToken(@RequestBody TokenReissueRequest request) {
		return jwtService.reissueToken(request);
	}

	/**
	 * 네이버 로그인
	 *
	 * @param request 네이버 로그인 요청
	 * @return 로그인 응답
	 */
	@NamonerResponse
	@PostMapping("/naver")
	public LoginResponse naverLogin(@RequestBody NaverLoginRequest request) {
		return naverOAuthService.getUserInfo(request);
	}
}
