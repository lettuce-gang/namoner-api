package com.toy.namoneo.letter.controller.dto.request;

import lombok.Data;

@Data
public class LetterSendRequest {
    private String recievePhoneNumber;
    private String letterSender;
    private String letterReceiver;
    private String text;
}
