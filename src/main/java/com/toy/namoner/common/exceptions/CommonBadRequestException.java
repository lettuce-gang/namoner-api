package com.toy.namoner.common.exceptions;

import lombok.Getter;

@Getter
public class CommonBadRequestException extends RuntimeException {
    CommonResponseCode responseCode;
    private String detailDescription;


    public CommonBadRequestException (CommonResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public CommonBadRequestException(CommonResponseCode responseCode, String detailDescription) {
        this.responseCode = responseCode;
        this.detailDescription = detailDescription;
    }

}
