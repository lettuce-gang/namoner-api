package com.toy.namoner.auth.jwt;

import com.toy.namoner.common.exceptions.CommonBadRequestException;
import com.toy.namoner.common.exceptions.CommonResponseCode;
import com.toy.namoner.user.domain.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Slf4j
public class JwtUtils {

    public static String generateAccessToken(final Key ACCESS_KEY, long ACCESS_EXPIRATION, User user) {
        Long now = System.currentTimeMillis();

        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(user))
                .setSubject(String.valueOf(user.getId()))
                .setExpiration(new Date(now + ACCESS_EXPIRATION))
                .signWith(ACCESS_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String generateRefreshToken(final Key REFRESH_KEY, final long REFRESH_EXPIRATION, User user) {
        Long now = System.currentTimeMillis();

        return Jwts.builder()
                .setHeader(createHeader())
                .setSubject(user.getId())
                .setExpiration(new Date(now + REFRESH_EXPIRATION))
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
            throw new CommonBadRequestException(CommonResponseCode.COMMON_BAD_REQUEST, "Wrong token request");
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
