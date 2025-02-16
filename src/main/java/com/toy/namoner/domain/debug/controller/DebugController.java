package com.toy.namoner.domain.debug.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.toy.namoner.common.handler.NamonerResponse;
import com.toy.namoner.common.jwt.JwtService;
import com.toy.namoner.domain.auth.controller.dto.response.NMNToken;
import com.toy.namoner.domain.auth.role.UserRole;
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
	 * @param userId 사용자 ID
	 * @param phone  전화번호
	 * @param role   사용자 권한
	 * @return 로그인 응답
	 */
	@NamonerResponse
	@PostMapping("/login")
	public NMNToken login(@RequestParam("userId") String userId,
		@RequestParam("phone") String phone,
		@RequestParam("role") UserRole role) {
		return jwtService.generateToken(User.builder()
			.id(userId)
			.phone(phone)
			.role(role)
			.build());
	}
}
