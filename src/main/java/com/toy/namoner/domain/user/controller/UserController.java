package com.toy.namoner.domain.user.controller;

import com.toy.namoner.common.jwt.NMNAuthentication;
import com.toy.namoner.domain.auth.role.UserAuth;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toy.namoner.common.error.exceptions.AuthorizationException;
import com.toy.namoner.common.handler.NamonerResponse;
import com.toy.namoner.domain.user.controller.dto.request.UserInfoUpdateRequest;
import com.toy.namoner.domain.user.controller.dto.response.PostBoxResponse;
import com.toy.namoner.domain.user.controller.dto.response.UserIdResponse;
import com.toy.namoner.domain.user.controller.dto.response.UserInfoUpdateResponse;
import com.toy.namoner.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	/**
	 * 사용자의 편지함 정보 조회
	 *
	 * @param userId 사용자 아이디
	 * @return 편지함 정보
	 */
	@NamonerResponse
	@GetMapping("/postbox/{userId}")
	public PostBoxResponse getPostBoxResponse(NMNAuthentication authentication, @PathVariable("userId") String userId) {
		return userService.findPostBoxByUserId(authentication, userId);
	}

	/**
	 * 전화번호로 사용자의 아이디 조회
	 *
	 * @param phoneNumber 사용자 전화번호
	 * @return 사용자 아이디
	 */
	@NamonerResponse
	@GetMapping("/phone/{phoneNumber}")
	public UserIdResponse getUserIdByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
		return userService.getUserIdResponseByPhoneNumber(phoneNumber);
	}

	/**
	 * 사용자 정보 수정
	 *
	 * @param request 사용자 정보
	 * @return 업데이트된 사용자 정보
	 */
	@NamonerResponse
	@UserAuth
	@PostMapping("/info")
	public UserInfoUpdateResponse updateUserInfo(NMNAuthentication authentication, @RequestBody UserInfoUpdateRequest request) {
		return userService.update(authentication, request);
	}
}
