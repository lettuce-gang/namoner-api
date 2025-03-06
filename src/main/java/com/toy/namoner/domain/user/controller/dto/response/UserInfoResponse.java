package com.toy.namoner.domain.user.controller.dto.response;

import com.toy.namoner.domain.user.model.User;
import com.toy.namoner.domain.user.model.UserConfig;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class UserInfoResponse {
	private final String postBoxName;
	private final UserConfig userConfig;
	private final String userId;
	public static UserInfoResponse from(User user) {
		return UserInfoResponse.builder()
				.postBoxName(user.getPostboxName())
				.userConfig(user.getUserConfig())
				.userId(user.getId())
				.build();
	}

}
