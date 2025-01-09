package com.toy.namoner.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.toy.namoner.common.AppEnvironment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AwsS3Config {

	private final AppEnvironment env;

	@Bean
	public AmazonS3Client amazonS3Client() {
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(env.getAccessKey(), env.getSecretKey());
		return (AmazonS3Client)AmazonS3ClientBuilder.standard()
			.withRegion(env.getS3Region())
			.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
			.build();
	}

}
