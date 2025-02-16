package com.toy.namoner.common.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.toy.namoner.common.error.exceptions.AuthorizationException;
import com.toy.namoner.domain.user.model.User;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtils {
	public static final String AUTHORIZATION_HEADER = "Authorization";

	public static String generateAccessToken(final Key accessKey, Date expiration, User user) {
		return Jwts.builder()
			.setHeader(createHeader())
			.setClaims(createClaims(user))
			.setSubject(String.valueOf(user.getId()))
			.setExpiration(expiration)
			.signWith(accessKey, SignatureAlgorithm.HS256)
			.compact();
	}

	public static String generateRefreshToken(final Key refreshKey, Date expiration, User user) {
		return Jwts.builder()
			.setHeader(createHeader())
			.setSubject(user.getId())
			.setExpiration(expiration)
			.signWith(refreshKey, SignatureAlgorithm.HS256)
			.compact();
	}

	private static Map<String, Object> createHeader() {
		Map<String, Object> header = new HashMap<>();
		header.put("typ", "JWT");
		header.put("alg", "HS256");
		return header;
	}

	private static Map<String, Object> createClaims(User user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", user.getId());
		claims.put("phoneNumber", user.getPhone());
		claims.put("Role", user.getRole());
		return claims;
	}

	public static boolean isValidToken(String token, Key secretKey) {
		if (token == null)
			return false;
		try {
			Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException | IllegalArgumentException e) {
			log.error("Token has expired");
			return false;
		} catch (JwtException e) {
			throw new AuthorizationException("Token is not valid");
		}
	}

	public static Key getSigningKey(String secretKey) {
		String encodedKey = encodeToBase64(secretKey);
		return Keys.hmacShaKeyFor(encodedKey.getBytes(StandardCharsets.UTF_8));
	}

	private static String encodeToBase64(String secretKey) {
		return Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public static String getUserId(String token, Key secretKey) {
		return Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}
}
