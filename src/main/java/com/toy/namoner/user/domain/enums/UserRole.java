package com.toy.namoner.user.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    USER("ROLE_USER", "USER", "일반 사용자"), ADMIN("ROLE_ADMIN", "ADMIN", "관리자"),
    ;

    private final String key;
    private final String value;
    private final String title;
}