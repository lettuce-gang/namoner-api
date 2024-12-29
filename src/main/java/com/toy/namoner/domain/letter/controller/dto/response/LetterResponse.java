package com.toy.namoner.domain.letter.controller.dto.response;

import com.toy.namoner.domain.letter.model.Letter;
import com.toy.namoner.domain.letter.model.enums.FontType;
import com.toy.namoner.domain.letter.model.enums.LetterPaperType;
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
