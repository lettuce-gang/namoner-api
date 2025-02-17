package com.toy.namoner.domain.letter.controller.dto.response;

import com.toy.namoner.domain.letter.model.Letter;
import com.toy.namoner.domain.letter.model.enums.FontType;
import com.toy.namoner.domain.letter.model.enums.LetterPaperType;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class LetterResponse {
    private final String letterReceiver;
    private final String letterSender;
    private final String message;
    private final String imageUrl;
    private final FontType fontType;
    private final LetterPaperType letterPaperType;
    private final Boolean isCanReply;
    private final LetterResponse reply;

    public static LetterResponse createLetterWithReply(Letter originalLetter, String originalLetterImage, Letter replyLetter, String replyLetterImage) {
        LetterResponse reply = LetterResponse.create(replyLetter, replyLetterImage);
        return createWithReply(originalLetter, originalLetterImage, reply);
    }


    public static LetterResponse create(Letter letter, String fullPathImageUrl) {
        return LetterResponse.builder()
                .letterReceiver(letter.getLetterReceiver())
                .letterSender(letter.getLetterSender())
                .message(letter.getMessage())
                .imageUrl(fullPathImageUrl)
                .fontType(letter.getFontType())
                .letterPaperType(letter.getLetterPaperType())
                .isCanReply(letter.isCanReply())
                .build();
    }

    private static LetterResponse createWithReply(Letter letter, String fullPathImageUrl, LetterResponse reply) {
        return LetterResponse.builder()
                .letterReceiver(letter.getLetterReceiver())
                .letterSender(letter.getLetterSender())
                .message(letter.getMessage())
                .imageUrl(fullPathImageUrl)
                .fontType(letter.getFontType())
                .letterPaperType(letter.getLetterPaperType())
                .isCanReply(letter.isCanReply())
                .reply(reply)
                .build();
    }
}
