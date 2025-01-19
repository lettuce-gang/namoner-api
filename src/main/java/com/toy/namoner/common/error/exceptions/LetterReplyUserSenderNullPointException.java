package com.toy.namoner.common.error.exceptions;

import org.springframework.http.HttpStatus;

public class LetterReplyUserSenderNullPointException extends TraceErrorException {
	public LetterReplyUserSenderNullPointException() {
		super(HttpStatus.BAD_REQUEST);
	}
}
