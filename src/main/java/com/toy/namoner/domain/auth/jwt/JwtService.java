package com.toy.namoner.domain.auth.jwt;

import com.toy.namoner.domain.auth.controller.dto.request.TokenReissueRequest;
import com.toy.namoner.domain.auth.controller.dto.response.NMNTokenResponse;
import com.toy.namoner.common.exceptions.AuthorizationException;
import com.toy.namoner.domain.user.model.User;
import com.toy.namoner.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {
    private final CustomUserDetailService customUserDetailService;
    private final UserService userService;
    private final Key ACCESS_SECRET_KEY;
    private final Key REFRESH_SECRET_KEY;
    private final long ACCESS_EXPIRATION;
    private final long REFRESH_EXPIRATION;

    private final String COOKIE_ACCESS_KEY = "accessToken";

    public JwtService(
            CustomUserDetailService customUserDetailService,
            UserService userService,
            @Value("${nmn.security.access-secret}") String accessTokenSecret,
            @Value("${nmn.security.access-expiration}") long accessTokenExpiration,
            @Value("${nmn.security.refresh-secret}") String refreshTokenSecret,
            @Value("${nmn.security.refresh-expiration}") long refreshTokenExpiration
    ) {
        this.customUserDetailService = customUserDetailService;
        this.userService = userService;
        this.ACCESS_SECRET_KEY = JwtUtils.getSigningKey(accessTokenSecret);
        this.REFRESH_SECRET_KEY = JwtUtils.getSigningKey(refreshTokenSecret);
        this.ACCESS_EXPIRATION = accessTokenExpiration;
        this.REFRESH_EXPIRATION = refreshTokenExpiration;
    }


    public String generateAccessToken(User user) {
        return JwtUtils.generateAccessToken(ACCESS_SECRET_KEY, ACCESS_EXPIRATION, user);
    }

    public String generateRefreshToken(User user) {
        // TODO refreshToken 생성 시 user별 token 저장 로직 필요
        return JwtUtils.generateRefreshToken(REFRESH_SECRET_KEY, REFRESH_EXPIRATION, user);
    }

    public String resolveAccessTokenFromHeader(HttpServletRequest request) {
        String accessToken = request.getHeader(COOKIE_ACCESS_KEY);

        return accessToken;
    }

    public boolean validateAccessToken(String accessToken) {
        return JwtUtils.isValidToken(accessToken, ACCESS_SECRET_KEY);
    }

    public Authentication getAuthentication(String token) {
        UserDetails principal = customUserDetailService.loadUserByUsername(JwtUtils.getUserId(token, ACCESS_SECRET_KEY));
        return new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
    }


    public boolean validateRefreshToken(String refreshToken) {
        if (!JwtUtils.isValidToken(refreshToken, REFRESH_SECRET_KEY)) {
            return false;
        }

        //TODO 리프레시 토큰 저장해서 값 비교 로직 추가 필요
        String userId = JwtUtils.getUserId(refreshToken, REFRESH_SECRET_KEY);

        return true;
    }

    public String getUserIdFromRefreshToken(String refreshToken) {
        return JwtUtils.getUserId(refreshToken, REFRESH_SECRET_KEY);
    }

    public NMNTokenResponse reissueToken(TokenReissueRequest request) {
        String refreshToken = request.getRefreshToken();

        if (!validateRefreshToken(refreshToken)) {
            throw new AuthorizationException("Refresh token is not valid");
        }

        String userId = getUserIdFromRefreshToken(refreshToken);
        User user = userService.findByUserId(userId);

        String accessToken = generateAccessToken(user);
        refreshToken = generateRefreshToken(user);

        return NMNTokenResponse.create(accessToken, refreshToken, ACCESS_EXPIRATION);
    }
}
