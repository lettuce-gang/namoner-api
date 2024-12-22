package com.toy.namoner.common.exceptions.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class BadRequestBodyDto {
    private int code;
    private String description;
    private String detailDescription;


}
