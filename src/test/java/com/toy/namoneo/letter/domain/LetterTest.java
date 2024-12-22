package com.toy.namoneo.letter.domain;

import com.toy.namoneo.letter.controller.dto.request.LetterSendRequest;
import com.toy.namoneo.letter.domain.enums.FontType;
import com.toy.namoneo.letter.domain.enums.LetterPaperType;
import com.toy.namoneo.letter.domain.enums.LetterType;
import com.toy.namoneo.user.domain.User;
import org.assertj.core.api.Assertions;

import java.time.LocalDateTime;

class LetterTest {

    public void createLetterByLetterSendRequest() {
        //given
        String userReceiver = "test-user01";
        String letterSender = "보낸이";
        String letterReceiver = "받은이";
        String message = "편지내용";
        LetterPaperType letterPaperType = LetterPaperType.GRAPH_PAPER;
        FontType fontType = FontType.Pretendard_R;
        LetterType letterType = LetterType.NORMAL;
        LocalDateTime receiveDate = LocalDateTime.now();

        LetterSendRequest letterSendRequest = LetterSendRequest.builder()
                .userReceiver(userReceiver)
                .letterSender(letterSender)
                .letterReceiver(letterReceiver)
                .message(message)
                .letterPaperType(letterPaperType)
                .fontType(fontType)
                .letterType(letterType)
                .receiveDate(receiveDate)
                .build();

        String imageUrl = "testUrl";

        User user = User.builder()
                .id(userReceiver)
                .build();
        //when
        Letter letter = Letter.createBySend(letterSendRequest, user, imageUrl);

        //then
        Assertions.assertThat(letter.getUserReceiver()).isEqualTo(user);
        Assertions.assertThat(letter.getLetterSender()).isEqualTo(letterSender);
        Assertions.assertThat(letter.getLetterReceiver()).isEqualTo(letterReceiver);
        Assertions.assertThat(letter.getMessage()).isEqualTo(message);
        Assertions.assertThat(letter.getLetterPaperType()).isEqualTo(letterPaperType);
        Assertions.assertThat(letter.getFontType()).isEqualTo(fontType);
        Assertions.assertThat(letter.getLetterType()).isEqualTo(letterType);
        Assertions.assertThat(letter.getReceiveDate()).isEqualTo(receiveDate);
        Assertions.assertThat(letter.getImageUrl()).isEqualTo(imageUrl);
        Assertions.assertThat(letter.getIsRead()).isFalse();

    }
}