package com.toy.namoner.domain.user.controller.dto.request;

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
}
