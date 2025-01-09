package com.toy.namoner.domain.auth.role;

import org.springframework.security.access.annotation.Secured;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Secured(UserRoleConstants.ADMIN_KEY)
public @interface AdminAuth {

}
