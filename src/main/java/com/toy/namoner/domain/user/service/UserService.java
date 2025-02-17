package com.toy.namoner.domain.user.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toy.namoner.common.error.exceptions.EntityNotFoundException;
import com.toy.namoner.common.error.exceptions.UserAlreadyJoinException;
import com.toy.namoner.common.error.exceptions.UserNotAllowedException;
import com.toy.namoner.common.jwt.NMNAuthentication;
import com.toy.namoner.common.utils.PhoneNumberUtils;
import com.toy.namoner.domain.stat.model.UserStat;
import com.toy.namoner.domain.stat.model.enums.UserActionType;
import com.toy.namoner.domain.stat.repository.StatRepository;
import com.toy.namoner.domain.user.controller.dto.request.UserJoinRequest;
import com.toy.namoner.domain.user.controller.dto.response.PostBoxResponse;
import com.toy.namoner.domain.user.controller.dto.response.UserIdResponse;
import com.toy.namoner.domain.user.controller.dto.response.UserInfoUpdateResponse;
import com.toy.namoner.domain.user.model.User;
import com.toy.namoner.domain.user.model.UserJoin;
import com.toy.namoner.domain.user.repository.UserRepository;
import com.toy.namoner.infra.service.dto.OAuthUserInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final StatRepository statRepository;

	public User findOrCreateByOAuth(OAuthUserInfo info) {
		final String nmnSpecPhoneNumber = PhoneNumberUtils.convertPhoneNumberToNMNSpec(info.getPhoneNum());

		return userRepository.findByPhone(nmnSpecPhoneNumber)
			.orElseGet(() -> createNotRegisteredUser(UserJoin.fromOAuth(info)));
	}

	public User findOrCreateByPhoneNumber(String phoneNumber) {
		final String nmnSpecPhoneNumber = PhoneNumberUtils.convertPhoneNumberToNMNSpec(phoneNumber);

		return userRepository.findByPhone(nmnSpecPhoneNumber)
			.orElseGet(() -> createNotRegisteredUser(UserJoin.fromPhoneNumber(nmnSpecPhoneNumber)));
	}

	public int getLettersCountByPhoneNumber(String phoneNumber) {
		Optional<User> optionalUser = userRepository.findByPhone(phoneNumber);
		return optionalUser.map(User::getReceiveLettersCount).orElse(0);
	}

	public User createNotRegisteredUser(UserJoin userJoin) {
		return userRepository.save(User.from(userJoin));
	}

	public User findByUserId(String userId) {
		if (StringUtils.isBlank(userId)) {
			throw new IllegalArgumentException("Invalid userId");
		}

		return userRepository.findById(userId)
			.orElseThrow(() -> new EntityNotFoundException("User " + userId + " not found"));
	}

	public UserInfoUpdateResponse join(NMNAuthentication authentication, UserJoinRequest updateInfo) {
		User user = this.findByUserId(authentication.getUserId());
		if (user.isSignedUser()) {
			throw new UserAlreadyJoinException("User " + user.getId() + " already joined");
		}

		user.firstUpdateUserInfo(updateInfo);
		userRepository.save(user);

		statRepository.logUser(UserStat.builderFrom(user)
			.actionType(UserActionType.JOIN)
			.gender(user.getGender())
			.age(user.getAge())
			.referrer(updateInfo.getReferrer())
			.build());

		return UserInfoUpdateResponse.from(user);
	}

	public PostBoxResponse findPostBoxByUserId(NMNAuthentication authentication, String userId) {
		User user = findByUserId(userId);

		if (authentication.verifyUser(user)) {
			return PostBoxResponse.createOwnerPostBox(user);
		}

		if (!user.isSignedUser()) {
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

	@Transactional
	public UserIdResponse withdraw(NMNAuthentication authentication) {
		User user = findByUserId(authentication.getUserId());
		user.setToDisable();

		statRepository.logUser(UserStat.builderFrom(user)
			.actionType(UserActionType.WITHDRAW)
			.gender(user.getGender())
			.age(user.getAge())
			.referrer("withdraw")
			.build());

		return UserIdResponse.from(user);
	}
}
