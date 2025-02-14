package com.toy.namoner.domain.user.service;

import java.util.Optional;

import com.toy.namoner.common.error.exceptions.UserNotAllowedException;
import com.toy.namoner.common.jwt.NMNAuthentication;
import com.toy.namoner.domain.user.controller.dto.response.PostBoxResponse;
import com.toy.namoner.domain.user.controller.dto.response.UserIdResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.toy.namoner.common.error.exceptions.EntityNotFoundException;
import com.toy.namoner.common.utils.PhoneNumberUtils;
import com.toy.namoner.domain.user.controller.dto.request.UserInfoUpdateRequest;
import com.toy.namoner.domain.user.controller.dto.response.UserInfoUpdateResponse;
import com.toy.namoner.domain.user.model.User;
import com.toy.namoner.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;

	public User findOrCreateByPhoneNumber(String phoneNumber) {
		final String nmnSpecPhoneNumber = PhoneNumberUtils.convertPhoneNumberToNMNSpec(phoneNumber);

		return userRepository.findByPhone(nmnSpecPhoneNumber)
			.orElseGet(() -> createNotRegisteredUser(nmnSpecPhoneNumber));
	}

	public Optional<User> findByPhoneNumber(String phoneNumber) {
		phoneNumber = PhoneNumberUtils.convertPhoneNumberToNMNSpec(phoneNumber);
		return userRepository.findByPhone(phoneNumber);
	}

	public int getLettersCountByPhoneNumber(String phoneNumber) {
		Optional<User> optionalUser = userRepository.findByPhone(phoneNumber);
		return optionalUser.map(User::getReceiveLettersCount).orElse(0);
	}

	public User createNotRegisteredUser(String phoneNumber) {
		phoneNumber = PhoneNumberUtils.convertPhoneNumberToNMNSpec(phoneNumber);
		User user = User.craeteNotRegisteredUser(phoneNumber);

		return userRepository.save(user);
	}

	public User findByUserId(String userId) {
		if (StringUtils.isBlank(userId)) {
			throw new IllegalArgumentException("Invalid userId");
		}

		return userRepository.findById(userId)
			.orElseThrow(() -> new EntityNotFoundException("User " + userId + " not found"));
	}

	public UserInfoUpdateResponse update(NMNAuthentication authentication, UserInfoUpdateRequest updateInfo) {
		User user = this.findByUserId(authentication.getUserId());

		user.firstUpdateUserInfo(updateInfo);

		userRepository.save(user);

		return UserInfoUpdateResponse.from(user);
	}

	public PostBoxResponse findPostBoxByUserId(NMNAuthentication authentication, String userId) {
		User user = findByUserId(userId);

		if (authentication.verifyUser(user)) {
			return PostBoxResponse.createOwnerPostBox(user);
		}

		if (user.isNotSignedUser()) {
			return PostBoxResponse.createGuestPostBox();
		}

		return PostBoxResponse.createNonOwnerPostBox(user);
	}

	public UserIdResponse getUserIdResponseByPhoneNumber(String phoneNumber) {
		User user = findOrCreateByPhoneNumber(phoneNumber);

		if (!user.getIsPhoneConnected()) {
			throw new UserNotAllowedException("User " + phoneNumber + " is not allowed to access");
		}
		return UserIdResponse.from(user);
	}
}
