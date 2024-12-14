package com.toy.namoneo.letter.controller.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class LetterSendRequest {
    private String toPhoneNumber;
    private String letterSender;
    private String letterReceiver;
    private String text;
}
