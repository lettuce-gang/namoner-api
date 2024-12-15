package com.toy.namoneo.letter.domain;

import com.toy.namoneo.letter.controller.dto.request.LetterSendRequest;
import com.toy.namoneo.letter.domain.enums.FontType;
import com.toy.namoneo.letter.domain.enums.LetterPaperType;
import com.toy.namoneo.user.domain.User;
import org.assertj.core.api.Assertions;

class LetterTest {

    public void createLetterByLetterSendRequest() {
        //given
        String recievePhoneNumber = "01011111111";
        String letterSender = "보낸이";
        String letterReceiver = "받은이";
        String message = "편지내용";
        LetterPaperType letterPaperType = LetterPaperType.GRAPH_PAPER;
        FontType fontType = FontType.Pretendard_R;
        LetterSendRequest letterSendRequest = LetterSendRequest.builder()
                .receiverPhoneNumber(recievePhoneNumber)
                .letterSender(letterSender)
                .letterReceiver(letterReceiver)
                .message(message)
                .letterPaperType(letterPaperType)
                .fontType(fontType)
                .build();

        String imageUrl = "testUrl";

        User user = User.builder()
                .id("tempUser")
                .build();
        //when
        Letter letter = Letter.from(letterSendRequest, user, imageUrl);

        //then
        Assertions.assertThat(letter.getUserReceiver()).isEqualTo("tempUser");
        Assertions.assertThat(letter.getLetterSender()).isEqualTo(letterSender);
        Assertions.assertThat(letter.getLetterReceiver()).isEqualTo(letterReceiver);
        Assertions.assertThat(letter.getMessage()).isEqualTo(message);

    }
}