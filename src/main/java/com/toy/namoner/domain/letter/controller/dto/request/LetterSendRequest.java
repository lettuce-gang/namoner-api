package com.toy.namoner.domain.letter.controller.dto.request;

import com.toy.namoner.domain.letter.model.enums.FontType;
import com.toy.namoner.domain.letter.model.enums.LetterPaperType;
import com.toy.namoner.domain.letter.model.enums.LetterType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LetterSendRequest {
    private String userReceiver;
    private String letterSender;
    private String letterReceiver;
    private String message;
    private LetterPaperType letterPaperType;
    private FontType fontType;
    private LetterType letterType;
    private LocalDateTime receiveDate;
}
