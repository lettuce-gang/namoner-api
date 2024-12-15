package com.toy.namoneo.letter.controller.dto.request;

import com.toy.namoneo.letter.domain.enums.FontType;
import com.toy.namoneo.letter.domain.enums.LetterPaperType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LetterSendRequest {
    private String receiverPhoneNumber;
    private String letterSender;
    private String letterReceiver;
    private String message;
    private LetterPaperType letterPaperType;
    private FontType fontType;
}
