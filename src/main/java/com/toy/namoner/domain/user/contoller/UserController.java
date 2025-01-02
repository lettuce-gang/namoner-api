package com.toy.namoner.domain.user.contoller;

import com.toy.namoner.domain.user.contoller.dto.response.UserIdResponse;
import com.toy.namoner.domain.user.model.User;
import com.toy.namoner.domain.user.service.UserService;
import com.toy.namoner.domain.user.contoller.dto.response.PostBoxResponse;

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
    public ResponseEntity<UserIdResponse> getUserIdByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        User user = userService.findOrCreateByPhoneNumber(phoneNumber);

        return ResponseEntity.ok(UserIdResponse.from(user.getId()));
    }
}
