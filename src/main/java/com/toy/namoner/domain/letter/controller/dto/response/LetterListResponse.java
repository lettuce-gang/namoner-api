package com.toy.namoner.domain.letter.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toy.namoner.domain.letter.model.Letter;
import com.toy.namoner.domain.letter.model.enums.LetterType;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Builder
public class LetterListResponse {
    private final String id;
    private final String letterReceiver;
    private final String letterSender;
    @JsonFormat(pattern = "yyyy.MM.dd hh:mm")
    private final LocalDateTime receiveDate;
    private final LetterType letterType;
    private final Boolean isRead;

    public static LetterListResponse from(Letter entity) {
        return LetterListResponse.builder()
                .id(entity.getId())
                .letterReceiver(entity.getLetterReceiver())
                .letterSender(entity.getLetterSender())
                .receiveDate(entity.getReceiveDate())
                .letterType(entity.getLetterType())
                .isRead(entity.getIsRead())
                .build();
    }

}
