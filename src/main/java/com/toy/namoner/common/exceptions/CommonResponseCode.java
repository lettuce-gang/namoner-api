package com.toy.namoner.common.exceptions;

public enum CommonResponseCode {
    COMMON_BAD_REQUEST(4000, "Bad Request"),
    ENTITY_NOT_FOUND(4001, "Entity Not Found"),
    LETTER_TYPE_EXCEPTION(4002, "LetterType shoud be in (NORMAL, RESERVED)"),
    ;
    private int code;
    private String description;

    CommonResponseCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }
    public String getDescription() {
        return description;
    }
}
