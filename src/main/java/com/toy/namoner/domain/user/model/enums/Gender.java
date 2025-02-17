package com.toy.namoner.domain.user.model.enums;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
	MALE("M"), FEMALE("F"), UNKNOWN("U");

	private final String naverCode;

	public static Gender of(String naverCode) {
		return Arrays.stream(values())
			.filter(g -> StringUtils.equals(g.naverCode, naverCode))
			.findAny()
			.orElse(null);
	}
}
