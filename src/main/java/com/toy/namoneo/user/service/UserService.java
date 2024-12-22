package com.toy.namoneo.user.service;

import com.toy.namoneo.user.domain.User;

import java.util.Optional;

public interface UserService {
    User findOrCreateByPhoneNumber(String phoneNumber);

    Optional<User> findByPhoneNumber(String phoneNumber);

    int getLettersCountByPhoneNumber(String phoneNumber);

    User createNotRegisteredUser(String phoneNumber);
}
