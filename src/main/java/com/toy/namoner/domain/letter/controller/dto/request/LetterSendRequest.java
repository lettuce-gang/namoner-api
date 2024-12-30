package com.toy.namoner.domain.letter.controller.dto.request;

import com.toy.namoner.domain.letter.model.enums.FontType;
import com.toy.namoner.domain.letter.model.enums.LetterPaperType;
import com.toy.namoner.domain.letter.model.enums.LetterType;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Builder
public class LetterSendRequest {
    private final String userReceiver;
    private final String letterSender;
    private final String letterReceiver;
    private final String message;
    private final LetterPaperType letterPaperType;
    private final FontType fontType;
    private final LetterType letterType;
    private final LocalDateTime receiveDate;
}
