package com.toy.namoner.common.jwt;

import com.toy.namoner.domain.auth.role.UserRoleConstants;
import com.toy.namoner.domain.user.model.User;
import com.toy.namoner.domain.auth.role.UserRole;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class NMNAuthenticationImpl implements NMNAuthentication {
    private final String userId;
    private final UserRole userRole;

    public static NMNAuthenticationImpl create(User user) {
        return new NMNAuthenticationImpl(user.getId(), user.getRole());
    }

    public static Authentication createAnonymous() {
        return new NMNAuthenticationImpl(UserRoleConstants.GUEST_VALUE, UserRole.GUEST);
    }

    /**
     * 인증된 사용자의 ID를 반환합니다.
     * 인증되지 않은 경우 "GUEST"를 반환합니다.
     */
    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public UserRole getUserRole() {
        return userRole;
    }

    @Override
    public boolean isGuest() {
        return userRole == UserRole.GUEST;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> userRole.getKey());
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return userId;
    }

    @Override
    public Object getDetails() {
        return userId;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}
