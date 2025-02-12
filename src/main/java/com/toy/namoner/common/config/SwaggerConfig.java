package com.toy.namoner.common.config;

import org.springframework.context.annotation.Configuration;

import com.toy.namoner.common.jwt.JwtUtils;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(
	info = @io.swagger.v3.oas.annotations.info.Info(
		title = "Namoner 서버 API",
		version = SwaggerConfig.API_VERSION
	),
	servers = {
		@Server(url = "http://dev.namoner.site/api", description = "개발 서버"),
		@Server(url = "/", description = "로컬 서버")
	}
)
@SecurityScheme(
	name = JwtUtils.AUTHORIZATION_HEADER,
	type = SecuritySchemeType.HTTP,
	scheme = "Bearer",
	bearerFormat = "JWT",
	in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
	public static final String API_VERSION = "v1.0.0";
}
