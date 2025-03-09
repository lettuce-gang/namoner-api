package com.toy.namoner.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableElasticsearchAuditing
public class ElasticSearchConfig extends ElasticsearchConfiguration {

	@Value("${spring.elasticsearch.uris}")
	private final String url;
	@Value("${spring.elasticsearch.username}")
	private final String username;
	@Value("${spring.elasticsearch.password}")
	private final String password;

	@Override
	public ClientConfiguration clientConfiguration() {
		return ClientConfiguration.builder()
			.connectedTo(url.replaceAll("https?://", ""))
			.withBasicAuth(username, password)
			.build();
	}
}
