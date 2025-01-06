package com.toy.namoner.domain.user.contoller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toy.namoner.common.exceptions.AuthorizationException;
import com.toy.namoner.common.handler.NamonerResponse;
import com.toy.namoner.domain.user.contoller.dto.request.UserInfoUpdateRequest;
import com.toy.namoner.domain.user.contoller.dto.response.PostBoxResponse;
import com.toy.namoner.domain.user.contoller.dto.response.UserIdResponse;
import com.toy.namoner.domain.user.contoller.dto.response.UserInfoUpdateResponse;
import com.toy.namoner.domain.user.model.User;
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
	public PostBoxResponse getPostBoxResponse(@PathVariable("userId") String userId) {
		User user = userService.findByUserId(userId);

		return PostBoxResponse.from(user);
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
		User user = userService.findOrCreateByPhoneNumber(phoneNumber);

		return UserIdResponse.from(user.getId());
	}

	/**
	 * 사용자 정보 수정
	 *
	 * @param request 사용자 정보
	 * @return 업데이트된 사용자 정보
	 */
	@NamonerResponse
	@PostMapping("/info")
	public UserInfoUpdateResponse updateUserInfo(@RequestBody UserInfoUpdateRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();

		String userId = null;
		if (principal instanceof UserDetails) {
			userId = ((UserDetails)principal).getUsername();
		}

		if (userId == null) {
			throw new AuthorizationException("Wrong user!");
		}

		return userService.update(userId, request);
	}
}
