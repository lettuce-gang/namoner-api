package com.toy.namoner.auth.controller;

import com.toy.namoner.auth.controller.dto.request.NaverLoginRequest;
import com.toy.namoner.auth.controller.dto.response.LoginResponse;
import com.toy.namoner.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class authController {

    private final AuthService naverAuthService;

    @PostMapping("/naver")
    public ResponseEntity<Object> naverLogin(@RequestBody NaverLoginRequest request) {
        LoginResponse response = naverAuthService.login(request);

        return ResponseEntity.ok(response);
    }
}
