package com.toy.namoner.auth.clients;

import com.toy.namoner.auth.clients.dto.response.NaverProfileApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface NaverProfileApiClient {

    @GetExchange("/v1/nid/me")
    ResponseEntity<NaverProfileApiResponse> getProfile(@RequestHeader("Authorization") String token);
}
