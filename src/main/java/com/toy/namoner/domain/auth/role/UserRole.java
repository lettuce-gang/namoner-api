package com.toy.namoner.domain.auth.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    USER(UserRoleConstants.USER_KEY, UserRoleConstants.USER_VALUE, UserRoleConstants.USER_TITLE),
    ADMIN(UserRoleConstants.ADMIN_KEY, UserRoleConstants.ADMIN_VALUE, UserRoleConstants.ADMIN_TITLE),
    GUEST(UserRoleConstants.GUEST_KEY, UserRoleConstants.GUEST_VALUE, UserRoleConstants.GUEST_TITLE),
    ;

    private final String key;
    private final String value;
    private final String title;
}