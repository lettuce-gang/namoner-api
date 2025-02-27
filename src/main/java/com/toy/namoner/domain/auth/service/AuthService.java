package com.toy.namoner.domain.auth.service;

import org.springframework.stereotype.Service;

import com.toy.namoner.domain.auth.controller.dto.response.LoginResponse;
import com.toy.namoner.domain.auth.controller.dto.response.NMNToken;
import com.toy.namoner.common.jwt.JwtService;
import com.toy.namoner.domain.user.model.UserDetail;
import com.toy.namoner.infra.service.dto.OAuthUserInfo;
import com.toy.namoner.domain.user.model.User;
import com.toy.namoner.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserService userService;
	private final JwtService jwtService;

	public LoginResponse loginByOAuthInfo(OAuthUserInfo info) {
		User user = userService.findOrCreateByUserJoin(UserDetail.fromOAuth(info));

		NMNToken nmnToken = jwtService.generateToken(user);

		return user.isSignedUser()
			? LoginResponse.createRegularLoginResponse(nmnToken, user)
			: LoginResponse.createFirstLoginResponse(nmnToken, user);
	}

}
