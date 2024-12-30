package com.toy.namoner.domain.letter.controller.dto.response;

import com.toy.namoner.domain.letter.model.Letter;
import com.toy.namoner.domain.letter.model.enums.FontType;
import com.toy.namoner.domain.letter.model.enums.LetterPaperType;
import lombok.Builder;
import lombok.Data;
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

    public static LetterResponse from(Letter letter, String fullPathImageUrl) {
        return LetterResponse.builder()
                .letterReceiver(letter.getLetterReceiver())
                .letterSender(letter.getLetterSender())
                .message(letter.getMessage())
                .imageUrl(fullPathImageUrl)
                .fontType(letter.getFontType())
                .letterPaperType(letter.getLetterPaperType())
                .build();
    }
}
