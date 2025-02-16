package com.toy.namoner.domain.auth.role;

import org.springframework.security.access.annotation.Secured;

import java.lang.annotation.*;

import com.toy.namoner.common.jwt.JwtUtils;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SecurityRequirement(name = JwtUtils.AUTHORIZATION_HEADER)
@Secured(UserRoleConstants.ADMIN_KEY)
public @interface AdminAuth {

}
