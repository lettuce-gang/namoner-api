package com.toy.namoner.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Component
@RequiredArgsConstructor
public class AppEnvironment {
	// namoner
	@Value("${nmn.security.access-secret}")
	private final String accessTokenSecret;
	@Value("${nmn.security.access-expiration}")
	private final long accessTokenExpiration;
	@Value("${nmn.security.refresh-secret}")
	private final String refreshTokenSecret;
	@Value("${nmn.security.refresh-expiration}")
	private final long refreshTokenExpiration;

	// aws
	@Value("${cloud.aws.credentials.access-key}")
	private String accessKey;
	@Value("${cloud.aws.credentials.secret-key}")
	private String secretKey;
	@Value("${cloud.aws.region.static}")
	private String region;

	// oauth
	@Value("${oauth.naver.client.id}")
	private String clientId;
	@Value("${oauth.naver.client.secret}")
	private String clientSecret;

	// aws S3
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	@Value("${cloud.aws.region.static}")
	private String s3Region;
}
