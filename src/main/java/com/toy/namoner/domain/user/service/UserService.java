package com.toy.namoner.domain.user.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toy.namoner.common.error.exceptions.EntityNotFoundException;
import com.toy.namoner.common.error.exceptions.UserAlreadyJoinException;
import com.toy.namoner.common.error.exceptions.UserNotAllowedException;
import com.toy.namoner.common.jwt.NMNAuthentication;
import com.toy.namoner.domain.stat.model.UserStat;
import com.toy.namoner.domain.stat.model.enums.UserActionType;
import com.toy.namoner.domain.stat.repository.StatRepository;
import com.toy.namoner.domain.user.controller.dto.request.UserConfigRequest;
import com.toy.namoner.domain.user.controller.dto.request.UserJoinRequest;
import com.toy.namoner.domain.user.controller.dto.response.PostBoxResponse;
import com.toy.namoner.domain.user.controller.dto.response.UserIdResponse;
import com.toy.namoner.domain.user.controller.dto.response.UserInfoResponse;
import com.toy.namoner.domain.user.controller.dto.response.UserJoinResponse;
import com.toy.namoner.domain.user.model.User;
import com.toy.namoner.domain.user.model.UserConfig;
import com.toy.namoner.domain.user.model.UserDetail;
import com.toy.namoner.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final StatRepository statRepository;

	public User findOrCreateByUserJoin(UserDetail userDetail) {
		UserDetail userDetailEncrypted = userDetail.encrypt();
		return userRepository.findByPhone(userDetailEncrypted.getPhoneNum())
			.orElseGet(() -> userRepository.save(User.from(userDetailEncrypted)));
	}

	public int getLettersCountByPhoneNumber(String phoneNumber) {
		Optional<User> optionalUser = userRepository.findByPhone(phoneNumber);
		return optionalUser.map(User::getReceiveLettersCount).orElse(0);
	}

	public User findByUserId(String userId) {
		if (StringUtils.isBlank(userId)) {
			throw new IllegalArgumentException("Invalid userId");
		}

		return userRepository.findById(userId)
			.orElseThrow(() -> new EntityNotFoundException("User " + userId + " not found"));
	}

	@Transactional
	public UserJoinResponse join(NMNAuthentication authentication, UserJoinRequest updateInfo) {
		User user = this.findByUserId(authentication.getUserId());
		if (user.isSignedUser()) {
			throw new UserAlreadyJoinException("User " + user.getId() + " already joined");
		}

		// 사용자 가입처리
		user.join(updateInfo);

		statRepository.logUser(UserStat.builderFrom(user)
			.actionType(UserActionType.JOIN)
			.gender(user.getGender())
			.age(user.getAge())
			.referrer(updateInfo.getReferrer())
			.build());

		return UserJoinResponse.from();
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
		User user = findOrCreateByUserJoin(UserDetail.fromPhoneNumber(phoneNumber));

		if (!user.isShowPostBox()) {
			throw new UserNotAllowedException("User " + phoneNumber + " is not allowed to access");
		}
		return UserIdResponse.from(user);
	}

	@Transactional
	public UserIdResponse setConfig(NMNAuthentication authentication, UserConfigRequest request) {
		User user = findByUserId(authentication.getUserId());
		UserConfig userConfig = user.getUserConfigOrCreate();
		if (request.getShowPostbox() != null) {
			userConfig.setShowPostbox(request.getShowPostbox());
		}
		if (request.getReceiveLetter() != null) {
			userConfig.setReceiveLetter(request.getReceiveLetter());
		}
		if (request.getShowLetterCount() != null) {
			userConfig.setShowLetterCount(request.getShowLetterCount());
		}
		return UserIdResponse.from(user);
	}

	@Transactional
	public UserIdResponse withdraw(NMNAuthentication authentication, String reason) {
		User user = findByUserId(authentication.getUserId());
		user.updateToDisable();

		statRepository.logUser(UserStat.builderFrom(user)
			.actionType(UserActionType.WITHDRAW)
			.gender(user.getGender())
			.age(user.getAge())
			.referrer("withdraw")
			.reason(reason)
			.build());

		return UserIdResponse.from(user);
	}

	public UserInfoResponse getUserInfo(NMNAuthentication authentication) {
		User user = findByUserId(authentication.getUserId());
		return UserInfoResponse.from(user);
	}

	public UserInfoResponse updatePostBoxName(NMNAuthentication authentication, String postbox) {
		User user = findByUserId(authentication.getUserId());
		user.updatePostBoxName(postbox);

		userRepository.save(user);

		return UserInfoResponse.from(user);
	}
}
