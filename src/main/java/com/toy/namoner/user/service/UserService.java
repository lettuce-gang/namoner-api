package com.toy.namoner.user.service;

import com.toy.namoner.user.domain.User;

import java.util.Optional;

public interface UserService {
    User findOrCreateByPhoneNumber(String phoneNumber);

    Optional<User> findByPhoneNumber(String phoneNumber);

    int getLettersCountByPhoneNumber(String phoneNumber);

    User createNotRegisteredUser(String phoneNumber);

    User findByUserId(String userId);
}