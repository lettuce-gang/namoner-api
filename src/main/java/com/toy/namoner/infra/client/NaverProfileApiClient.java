package com.toy.namoner.infra.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import com.toy.namoner.common.jwt.JwtUtils;
import com.toy.namoner.infra.client.dto.response.NaverProfileApiResponse;

@HttpExchange
public interface NaverProfileApiClient {

    @GetExchange("/v1/nid/me")
    ResponseEntity<NaverProfileApiResponse> getProfile(@RequestHeader(JwtUtils.AUTHORIZATION_HEADER) String token);
}
