package com.toy.namoner.domain.user.contoller;

import com.toy.namoner.common.exceptions.AuthorizationException;
import com.toy.namoner.common.handler.NamonerResponse;
import com.toy.namoner.domain.user.contoller.dto.request.UserInfoUpdateRequest;
import com.toy.namoner.domain.user.contoller.dto.response.UserIdResponse;
import com.toy.namoner.domain.user.contoller.dto.response.UserInfoUpdateResponse;
import com.toy.namoner.domain.user.model.User;
import com.toy.namoner.domain.user.service.UserService;
import com.toy.namoner.domain.user.contoller.dto.response.PostBoxResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @NamonerResponse
    @GetMapping("/postbox/{userId}")
    public ResponseEntity<PostBoxResponse> getPostBoxResponse(@PathVariable("userId") String userId) {
        User user = userService.findByUserId(userId);

        return ResponseEntity.ok(PostBoxResponse.from(user));
    }

    @NamonerResponse
    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<UserIdResponse> getUserIdByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        User user = userService.findOrCreateByPhoneNumber(phoneNumber);

        return ResponseEntity.ok(UserIdResponse.from(user.getId()));
    }

    @NamonerResponse
    @PostMapping("/info")
    public UserInfoUpdateResponse updateUserInfo(@RequestBody UserInfoUpdateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        String userId = null;
        if (principal instanceof UserDetails) {
            userId = ((UserDetails) principal).getUsername();
        }

        if (userId == null) {
            throw new AuthorizationException("Wrong!");
        }


        UserInfoUpdateResponse response = userService.update(userId, request);

        return response;
    }
}
