package com.toy.namoner.domain.user.model;

import com.toy.namoner.domain.user.model.enums.Gender;
import com.toy.namoner.infra.service.dto.OAuthUserInfo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserJoin {
	private final String phoneNum;
	private final String age;
	private final Gender gender;

	public static UserJoin fromOAuth(OAuthUserInfo info) {
		return new UserJoin(info.getPhoneNum(), info.getAge(), info.getGender());
	}

	public static UserJoin fromPhoneNumber(String phoneNumber) {
		return new UserJoin(phoneNumber, null, null);
	}
}
