package com.toy.namoner.domain.user.model;

import com.toy.namoner.common.utils.EncryptionUtils;
import com.toy.namoner.common.utils.PhoneNumberUtils;
import com.toy.namoner.domain.user.model.enums.Gender;
import com.toy.namoner.infra.service.dto.OAuthUserInfo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDetail {
	private final String phoneNum;
	private final String age;
	private final Gender gender;

	public UserDetail encrypt() {
		return new UserDetail(EncryptionUtils.encrypt(phoneNum), age, gender);
	}

	public static UserDetail from(String phoneNumber, String age, Gender gender) {
		return new UserDetail(PhoneNumberUtils.convertPhoneNumberToNMNSpec(phoneNumber), age, gender);
	}

	public static UserDetail fromOAuth(OAuthUserInfo info) {
		return from(info.getPhoneNum(), info.getAge(), info.getGender());
	}

	public static UserDetail fromPhoneNumber(String phoneNumber) {
		return from(phoneNumber, null, null);
	}
}
