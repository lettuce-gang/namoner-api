package com.toy.namoneo.letter.controller.dto.request;

import com.toy.namoneo.letter.domain.enums.FontType;
import com.toy.namoneo.letter.domain.enums.LetterPaperType;
import com.toy.namoneo.letter.domain.enums.LetterType;
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
