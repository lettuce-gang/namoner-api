package com.toy.namoner.domain.auth.jwt;

import com.toy.namoner.domain.auth.controller.dto.request.TokenReissueRequest;
import com.toy.namoner.domain.auth.controller.dto.response.NMNToken;
import com.toy.namoner.common.exceptions.AuthorizationException;
import com.toy.namoner.domain.user.model.User;
import com.toy.namoner.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {
    private final CustomUserDetailService customUserDetailService;
    private final UserService userService;
    private final Key ACCESS_SECRET_KEY;
    private final Key REFRESH_SECRET_KEY;
    private final long ACCESS_EXPIRATION;
    private final long REFRESH_EXPIRATION;

    private final String COOKIE_ACCESS_KEY = "Authorization";

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

    public NMNToken generateToken(User user) {
        Date now = new Date();

        String accessToken = generateAccessToken(user, now);
        String refreshToken = generateRefreshToken(user, now);
        LocalDateTime accessTokenExpiredTime =
                new Date(now.getTime() + ACCESS_EXPIRATION)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return NMNToken.create(accessToken, refreshToken, accessTokenExpiredTime);
    }

    public String generateAccessToken(User user, Date baseTime) {
        Date expiration = new Date(baseTime.getTime() + ACCESS_EXPIRATION);
        return JwtUtils.generateAccessToken(ACCESS_SECRET_KEY, expiration, user);
    }

    public String generateRefreshToken(User user, Date baseTime) {
        // TODO refreshToken 생성 시 user별 token 저장 로직 필요
        Date expiration = new Date(baseTime.getTime() + REFRESH_EXPIRATION);
        return JwtUtils.generateRefreshToken(REFRESH_SECRET_KEY, expiration, user);
    }

    public String resolveAccessTokenFromHeader(HttpServletRequest request) {
        String headerValue = request.getHeader(COOKIE_ACCESS_KEY);

        if (headerValue != null && headerValue.startsWith("Bearer ")) {
            return headerValue.substring(7);
        }

        return null;
    }

    public boolean validateAccessToken(String accessToken) {
        return JwtUtils.isValidToken(accessToken, ACCESS_SECRET_KEY);
    }

    public Authentication getAuthentication(String token) {
        UserDetails principal = customUserDetailService.loadUserByUsername(JwtUtils.getUserId(token, ACCESS_SECRET_KEY));
        User user = userService.findByUserId(principal.getUsername());

        return NMNAuthenticationImpl.create(user);
    }

    public Authentication getAnonymousAuthentication() {
        return NMNAuthenticationImpl.createAnonymous();
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

    public NMNToken reissueToken(TokenReissueRequest request) {
        String refreshToken = request.getRefreshToken();

        if (!validateRefreshToken(refreshToken)) {
            throw new AuthorizationException("Refresh token is not valid");
        }

        String userId = getUserIdFromRefreshToken(refreshToken);
        User user = userService.findByUserId(userId);

        return generateToken(user);
    }
}
