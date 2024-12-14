package com.toy.namoneo.user.service;

import com.toy.namoneo.common.utils.PhoneNumberUtils;
import com.toy.namoneo.user.domain.User;
import com.toy.namoneo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        phoneNumber = PhoneNumberUtils.convertPhoneNumberToNMNSpec(phoneNumber);
        return userRepository.findByPhone(phoneNumber);
    }
    @Override
    public int getLettersCountByPhoneNumber(String phoneNumber) {
        Optional<User> optionalUser = userRepository.findByPhone(phoneNumber);

        if (!optionalUser.isPresent()) {
            return 0;
        }

        User user = optionalUser.get();

        return user.getReceiveLettersCount();
    }

    @Override
    public User craeteNotRegisteredUser(String phoneNumber) {
        phoneNumber = PhoneNumberUtils.convertPhoneNumberToNMNSpec(phoneNumber);
        User user = User.craeteNotRegisteredUser(phoneNumber);

        return userRepository.save(user);
    }

}
