package com.toy.namoneo.letter.controller.dto.response;

import com.toy.namoneo.letter.domain.Letter;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LetterResponse {
    private String id;
    private String letterReceiver;
    private String letterSender;
    private String text;
    private String imageUrl;

    public static LetterResponse from(Letter entity, String fullPathUrl) {
        return LetterResponse.builder()
                .id(entity.getId())
                .letterReceiver(entity.getLetterReceiver())
                .letterSender(entity.getLetterSender())
                .text(entity.getMessage())
                .imageUrl(fullPathUrl)
                .build();
    }

}
