package com.toy.namoner.domain.user.controller.dto.request;

import com.toy.namoner.domain.user.model.UserConfig;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class UserConfigRequest {
	private final Boolean showPostbox;
	private final Boolean receiveLetter;
	private final Boolean showLetterCount;

	public void apply(UserConfig userConfig) {
		if (showPostbox != null) {
			userConfig.setShowPostbox(showPostbox);
		}
		if (receiveLetter != null) {
			userConfig.setReceiveLetter(receiveLetter);
		}
		if (showLetterCount != null) {
			userConfig.setShowLetterCount(showLetterCount);
		}
	}
}
