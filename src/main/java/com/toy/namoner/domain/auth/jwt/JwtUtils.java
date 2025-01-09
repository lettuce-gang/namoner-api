package com.toy.namoner.domain.auth.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
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
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtils {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static String generateAccessToken(final Key ACCESS_KEY, Date expiration, User user) {
        Long now = System.currentTimeMillis();

        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(user))
                .setSubject(String.valueOf(user.getId()))
                .setExpiration(expiration)
                .signWith(ACCESS_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String generateRefreshToken(final Key REFRESH_KEY, Date expiration, User user) {
        Long now = System.currentTimeMillis();

        return Jwts.builder()
                .setHeader(createHeader())
                .setSubject(user.getId())
                .setExpiration(expiration)
                .signWith(REFRESH_KEY, SignatureAlgorithm.HS256)
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
        if (token == null) return false;
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

    public static String resolveTokenFromCookie(Cookie[] cookies, String cookieKey) {
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(cookieKey))
                .findFirst()
                .map(Cookie::getValue)
                .orElse("");
    }

    public static Key getSigningKey(String secretKey) {
        String encodedKey = encodeToBase64(secretKey);
        return Keys.hmacShaKeyFor(encodedKey.getBytes(StandardCharsets.UTF_8));
    }

    private static String encodeToBase64(String secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public static Cookie resetTokenInCookie(String cookieKey) {
        Cookie cookie = new Cookie(cookieKey, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        return cookie;
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
