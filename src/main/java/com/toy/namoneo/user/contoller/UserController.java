package com.toy.namoneo.user.contoller;

import com.toy.namoneo.user.domain.User;
import com.toy.namoneo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<String> getUserIdByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        User user = userService.findOrCreateByPhoneNumber(phoneNumber);

        return ResponseEntity.ok(user.getId());
    }
}
