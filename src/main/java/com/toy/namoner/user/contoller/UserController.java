package com.toy.namoner.user.contoller;

import com.toy.namoner.user.contoller.dto.response.PostBoxResponse;
import com.toy.namoner.user.domain.User;
import com.toy.namoner.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/postbox/{userId}")
    public ResponseEntity<PostBoxResponse> getPostBoxResponse(@PathVariable("userId") String userId) {
        User user = userService.findByUserId(userId);

        return ResponseEntity.ok(PostBoxResponse.from(user));
    }
    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<String> getUserIdByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        User user = userService.findOrCreateByPhoneNumber(phoneNumber);

        return ResponseEntity.ok(user.getId());
    }
}
