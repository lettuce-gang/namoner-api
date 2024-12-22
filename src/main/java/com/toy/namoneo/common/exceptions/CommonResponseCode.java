package com.toy.namoneo.common.exceptions;

public enum CommonResponseCode {
    ENTITY_NOT_FOUND(4000, "Entity Not Found"),

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
