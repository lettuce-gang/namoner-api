package com.toy.namoner.letter.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toy.namoner.letter.domain.Letter;
import com.toy.namoner.letter.domain.enums.LetterType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LetterListResponse {
    private String id;
    private String letterReceiver;
    private String letterSender;
    @JsonFormat(pattern = "yyyy.MM.dd hh:mm")
    private LocalDateTime receiveDate;
    private LetterType letterType;
    private Boolean isRead;

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
