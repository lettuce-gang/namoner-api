package com.toy.namoner.common.exceptions;

public enum CommonResponseCode {
    ENTITY_NOT_FOUND(4000, "Entity Not Found"),
    LETTER_TYPE_EXCEPTION(4001, "LetterType shoud be in (NORMAL, RESERVED)"),
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
