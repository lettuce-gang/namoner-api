package com.toy.namoner.domain.auth.clients;

import com.toy.namoner.domain.auth.clients.dto.response.NaverTokenApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface NaverTokenApiClient {
    @GetExchange("/oauth2.0/token")
    ResponseEntity<NaverTokenApiResponse> getToken(
            @RequestParam("grant_type") String grantType, @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret, @RequestParam("code") String code,
            @RequestParam("state") String state);
}
