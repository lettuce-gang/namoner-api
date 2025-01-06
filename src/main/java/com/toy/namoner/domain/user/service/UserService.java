package com.toy.namoner.domain.user.service;

import java.util.Optional;

import com.toy.namoner.domain.user.contoller.dto.request.UserInfoUpdateRequest;
import com.toy.namoner.domain.user.contoller.dto.response.UserInfoUpdateResponse;
import com.toy.namoner.domain.user.model.User;

public interface UserService {
    User findOrCreateByPhoneNumber(String phoneNumber);

    Optional<User> findByPhoneNumber(String phoneNumber);

    int getLettersCountByPhoneNumber(String phoneNumber);

    User createNotRegisteredUser(String phoneNumber);

    User findByUserId(String userId);

    UserInfoUpdateResponse update(String userId, UserInfoUpdateRequest updateInfo);
}
