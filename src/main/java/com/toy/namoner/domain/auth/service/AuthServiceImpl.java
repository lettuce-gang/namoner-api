package com.toy.namoner.domain.auth.service;

import com.toy.namoner.domain.auth.controller.dto.response.LoginResponse;
import com.toy.namoner.domain.auth.jwt.JwtService;
import com.toy.namoner.domain.auth.service.dto.OAuthUserInfo;
import com.toy.namoner.domain.user.model.User;
import com.toy.namoner.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final JwtService jwtService;
    @Override
    public LoginResponse loginByOAuthInfo(OAuthUserInfo info) {
        User user = userService.findOrCreateByPhoneNumber(info.getPhoneNum());

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return user.isFirstLoginUser()
                ? LoginResponse.createFirstLoginResponse(accessToken, refreshToken)
                : LoginResponse.createLoginResponse(accessToken, refreshToken);
    }

}
