package com.toy.namoner.domain.auth.jwt;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.toy.namoner.common.AppEnvironment;
import com.toy.namoner.common.error.exceptions.AuthorizationException;
import com.toy.namoner.domain.auth.controller.dto.request.TokenReissueRequest;
import com.toy.namoner.domain.auth.controller.dto.response.NMNToken;
import com.toy.namoner.domain.user.model.User;
import com.toy.namoner.domain.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {
	private final CustomUserDetailService customUserDetailService;
	private final UserService userService;
	private final AppEnvironment env;

	public NMNToken generateToken(User user) {
		Date now = new Date();

		String accessToken = generateAccessToken(user, now);
		String refreshToken = generateRefreshToken(user, now);
		LocalDateTime accessTokenExpiredTime =
			new Date(now.getTime() + env.getAccessTokenExpiration())
				.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();

		return NMNToken.create(accessToken, refreshToken, accessTokenExpiredTime);
	}

	private Key getAccessSecretKey() {
		return JwtUtils.getSigningKey(env.getAccessTokenSecret());
	}

	private Key getRefreshSecretKey() {
		return JwtUtils.getSigningKey(env.getRefreshTokenSecret());
	}

	public String generateAccessToken(User user, Date baseTime) {
		Date expiration = new Date(baseTime.getTime() + env.getAccessTokenExpiration());
		return JwtUtils.generateAccessToken(getAccessSecretKey(), expiration, user);
	}

	public String generateRefreshToken(User user, Date baseTime) {
		// TODO refreshToken 생성 시 user별 token 저장 로직 필요
		Date expiration = new Date(baseTime.getTime() + env.getRefreshTokenExpiration());
		return JwtUtils.generateRefreshToken(getRefreshSecretKey(), expiration, user);
	}

	public String resolveAccessTokenFromHeader(HttpServletRequest request) {
		String headerValue = request.getHeader(JwtUtils.AUTHORIZATION_HEADER);

		if (headerValue != null && headerValue.startsWith("Bearer ")) {
			return headerValue.substring(7);
		}

		return null;
	}

	public boolean validateAccessToken(String accessToken) {
		return JwtUtils.isValidToken(accessToken, getAccessSecretKey());
	}

    public Authentication getAuthentication(String token) {
        UserDetails principal = customUserDetailService.loadUserByUsername(JwtUtils.getUserId(token, getAccessSecretKey()));
        User user = userService.findByUserId(principal.getUsername());

        return NMNAuthenticationImpl.create(user);
    }

    public Authentication getAnonymousAuthentication() {
        return NMNAuthenticationImpl.createAnonymous();
    }

	public boolean validateRefreshToken(String refreshToken) {
		if (!JwtUtils.isValidToken(refreshToken, getRefreshSecretKey())) {
			return false;
		}

		//TODO 리프레시 토큰 저장해서 값 비교 로직 추가 필요
		String userId = JwtUtils.getUserId(refreshToken, getRefreshSecretKey());

		return true;
	}

	public String getUserIdFromRefreshToken(String refreshToken) {
		return JwtUtils.getUserId(refreshToken, getRefreshSecretKey());
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
