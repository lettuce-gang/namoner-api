package com.toy.namoneo.user.service;

import com.toy.namoneo.user.domain.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByPhoneNumber(String phoneNumber);

    int getLettersCountByPhoneNumber(String phoneNumber);

    User craeteNotRegisteredUser(String phoneNumber);
}
