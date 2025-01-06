package com.toy.namoner.domain.auth.controller;

import com.toy.namoner.common.handler.NamonerResponse;
import com.toy.namoner.domain.auth.controller.dto.request.NaverLoginRequest;
import com.toy.namoner.domain.auth.controller.dto.request.TokenReissueRequest;
import com.toy.namoner.domain.auth.controller.dto.response.LoginResponse;
import com.toy.namoner.domain.auth.controller.dto.response.NMNToken;
import com.toy.namoner.domain.auth.jwt.JwtService;
import com.toy.namoner.domain.auth.service.oauth.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final OAuthService naverOAuthService;
    private final JwtService jwtService;

    @NamonerResponse
    @PostMapping("/reissue")
    public ResponseEntity<NMNToken> reissueToken(@RequestBody TokenReissueRequest request) {
        NMNToken response = jwtService.reissueToken(request);

        return ResponseEntity.ok(response);
    }

    @NamonerResponse
    @PostMapping("/naver")
    public ResponseEntity<Object> naverLogin(@RequestBody NaverLoginRequest request) {
        LoginResponse response = naverOAuthService.getUserInfo(request);

        return ResponseEntity.ok(response);
    }
}
