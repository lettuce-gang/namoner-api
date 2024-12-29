package com.toy.namoner.auth.service;

import com.toy.namoner.auth.controller.dto.request.TokenReissueRequest;
import com.toy.namoner.auth.controller.dto.response.LoginResponse;
import com.toy.namoner.auth.controller.dto.response.NMNTokenResponse;
import com.toy.namoner.auth.jwt.JwtService;
import com.toy.namoner.auth.service.dto.OAuthUserInfo;
import com.toy.namoner.common.exceptions.AuthorizationException;
import com.toy.namoner.common.exceptions.CommonBadRequestException;
import com.toy.namoner.user.domain.User;
import com.toy.namoner.user.service.UserService;
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
