package com.toy.namoner.domain.auth.clients;

import com.toy.namoner.common.logs.httpInterface.LoggingExchangeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class AuthApiClientsConfig {
    @Bean
    public NaverTokenApiClient naverTokenApiClients() {
        RestClient client = RestClient.builder()
                .requestInterceptor(new LoggingExchangeFilter())
                .baseUrl("https://nid.naver.com")
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(client);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(NaverTokenApiClient.class);
    }

    @Bean
    public NaverProfileApiClient naverProfileApiClients() {
        RestClient client = RestClient.builder()
                .requestInterceptor(new LoggingExchangeFilter())
                .baseUrl("https://openapi.naver.com")
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(client);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(NaverProfileApiClient.class);
    }
}
