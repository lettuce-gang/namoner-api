package com.toy.namoner.user.service;

import com.toy.namoner.common.exceptions.CommonBadRequestException;
import com.toy.namoner.common.exceptions.CommonResponseCode;
import com.toy.namoner.common.utils.PhoneNumberUtils;
import com.toy.namoner.user.domain.User;
import com.toy.namoner.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User findOrCreateByPhoneNumber(String phoneNumber) {
        final String nmnSpecPhoneNumber = PhoneNumberUtils.convertPhoneNumberToNMNSpec(phoneNumber);

        return userRepository.findByPhone(nmnSpecPhoneNumber)
                .orElseGet(() -> createNotRegisteredUser(nmnSpecPhoneNumber));
    }
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
    public User createNotRegisteredUser(String phoneNumber) {
        phoneNumber = PhoneNumberUtils.convertPhoneNumberToNMNSpec(phoneNumber);
        User user = User.craeteNotRegisteredUser(phoneNumber);

        return userRepository.save(user);
    }

    @Override
    public User findByUserId(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            String detailExceptionMessage = "User " + userId + " not found";
            return new CommonBadRequestException(CommonResponseCode.ENTITY_NOT_FOUND, detailExceptionMessage);
        });
    }

}
