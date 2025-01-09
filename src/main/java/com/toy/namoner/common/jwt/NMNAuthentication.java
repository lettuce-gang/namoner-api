package com.toy.namoner.common.jwt;

import com.toy.namoner.domain.auth.role.UserRole;
import org.springframework.security.core.Authentication;

public interface NMNAuthentication extends Authentication {
    String getUserId();

    UserRole getUserRole();

}
