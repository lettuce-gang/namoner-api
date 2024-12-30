package com.toy.namoner.domain.auth.jwt;

import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;

import java.util.List;

public class PermittedUrls {

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    public static List<String> getPermittedAllMethodUrls() {
        return List.of(
                "/auth/**"
        );
    }

    public static List<String> getPermittedPostMethodUrls() {
        return List.of(
                "/letters"
        );
    }

    public static List<String> getPermittedGetMethodUrls() {
        return List.of(
                "/users/postbox/**",
                "/users/phone/**"
        );
    }

    public static boolean isPermittedUrl(String method, String URI) {
        HttpMethod httpMethod = HttpMethod.valueOf(method);
        if (httpMethod == null) {
            return false; // 허용되지 않은 HTTP 메서드는 거부
        }

        // 모든 메서드 허용 URL 체크
        if (getPermittedAllMethodUrls().stream().anyMatch(url -> pathMatcher.match(url, URI))) {
            return true;
        }

        // 메서드별 허용 URL 체크
        if (httpMethod == HttpMethod.POST && getPermittedPostMethodUrls().stream().anyMatch(url -> pathMatcher.match(url, URI))) {
            return true;
        }
        if (httpMethod == HttpMethod.GET && getPermittedGetMethodUrls().stream().anyMatch(url -> pathMatcher.match(url, URI))) {
            return true;
        }

        return false;
    }
}
