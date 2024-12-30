package com.toy.namoner.domain.auth.clients;

import com.toy.namoner.domain.auth.clients.dto.response.NaverProfileApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface NaverProfileApiClient {

    @GetExchange("/v1/nid/me")
    ResponseEntity<NaverProfileApiResponse> getProfile(@RequestHeader("Authorization") String token);
}
