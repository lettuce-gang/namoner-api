package com.toy.namoner.letter.controller.dto.response;

import com.toy.namoner.letter.domain.Letter;
import com.toy.namoner.letter.domain.enums.FontType;
import com.toy.namoner.letter.domain.enums.LetterPaperType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LetterResponse {
    private String letterReceiver;
    private String letterSender;
    private String message;
    private String imageUrl;
    private FontType fontType;
    private LetterPaperType letterPaperType;

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
