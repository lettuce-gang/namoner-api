package com.toy.namoner.auth.controller;

import com.toy.namoner.auth.controller.dto.request.NaverLoginRequest;
import com.toy.namoner.auth.controller.dto.request.TokenReissueRequest;
import com.toy.namoner.auth.controller.dto.response.LoginResponse;
import com.toy.namoner.auth.controller.dto.response.NMNTokenResponse;
import com.toy.namoner.auth.jwt.JwtService;
import com.toy.namoner.auth.service.AuthService;
import com.toy.namoner.auth.service.oauth.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class authController {

    private final OAuthService naverOAuthService;

    private final JwtService jwtService;
    @PostMapping("/reissue")
    public ResponseEntity<NMNTokenResponse> reissueToken(@RequestBody TokenReissueRequest request) {
        NMNTokenResponse response = jwtService.reissueToken(request);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/naver")
    public ResponseEntity<Object> naverLogin(@RequestBody NaverLoginRequest request) {
        LoginResponse response = naverOAuthService.getUserInfo(request);

        return ResponseEntity.ok(response);
    }
}
