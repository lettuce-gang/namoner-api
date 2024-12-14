package com.toy.namoneo.user.service;

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
    public int getLettersCountByPhoneNumber(String phoneNumber) {
        Optional<User> optionalUser = userRepository.findByPhone(phoneNumber);

        if (!optionalUser.isPresent()) {
            return 0;
        }

        User user = optionalUser.get();

        return user.getReceiveLettersCount();
    }

}
