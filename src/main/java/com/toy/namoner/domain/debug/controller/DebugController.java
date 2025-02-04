package com.toy.namoner.domain.debug.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toy.namoner.common.handler.NamonerResponse;
import com.toy.namoner.common.jwt.JwtService;
import com.toy.namoner.domain.auth.controller.dto.response.NMNToken;
import com.toy.namoner.domain.user.model.User;

import lombok.RequiredArgsConstructor;

@Profile("default | dev")
@RestController
@RequestMapping("/debug")
@RequiredArgsConstructor
public class DebugController {

	private final JwtService jwtService;

	/**
	 * 테스트용 로그인 API.
	 * 더미 user데이터로 토큰을 발급합니다.
	 *
	 * @param user 로그인할 user 정보
	 * @return 로그인 응답
	 */
	@NamonerResponse
	@PostMapping("/login")
	public NMNToken login(@RequestBody User user) {
		return jwtService.generateToken(user);
	}
}
