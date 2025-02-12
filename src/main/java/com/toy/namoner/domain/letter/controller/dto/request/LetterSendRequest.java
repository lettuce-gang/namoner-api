package com.toy.namoner.domain.letter.controller.dto.request;

import java.time.LocalDateTime;

import com.toy.namoner.domain.letter.model.enums.FontType;
import com.toy.namoner.domain.letter.model.enums.LetterPaperType;
import com.toy.namoner.domain.letter.model.enums.LetterType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class LetterSendRequest {
    @NotEmpty
    private final String userReceiver;
    @NotEmpty
    private final String letterSender;
    @NotEmpty
    private final String letterReceiver;
    @NotEmpty
    private final String message;
    @NotNull
    private final LetterPaperType letterPaperType;
    @NotNull
    private final FontType fontType;
    @NotNull
    private final LetterType letterType;
    private final LocalDateTime receiveDate;
}
