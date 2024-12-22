package com.toy.namoneo.common.exceptions.dto;

import com.toy.namoneo.common.exceptions.CommonResponseCode;
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
