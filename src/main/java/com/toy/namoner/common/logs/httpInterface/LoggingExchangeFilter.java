package com.toy.namoner.common.logs.httpInterface;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
public class LoggingExchangeFilter implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);

        ClientHttpResponse response = execution.execute(request, body);
        ClientHttpResponse cachedResponse = cacheResponseBody(response); // 응답 본문 캐싱

        logResponse(cachedResponse);
        return cachedResponse; // 복원된 응답 반환
    }

    private void logRequest(HttpRequest request, byte[] body) {
        log.info("Request URI: {}", request.getURI());
        log.info("Request Method: {}", request.getMethod());
        log.info("Request Headers: {}", request.getHeaders());
        if (body.length > 0) {
            log.info("Request Body: {}", new String(body, StandardCharsets.UTF_8));
        }
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        String responseBody = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

        log.info("Response Status Code: {}", response.getStatusCode());
        log.info("Response Headers: {}", response.getHeaders());
        log.info("Response Body: {}", responseBody);
    }

    private ClientHttpResponse cacheResponseBody(ClientHttpResponse response) throws IOException {
        // 원래 InputStream 읽기
        byte[] body = response.getBody().readAllBytes();

        // 응답 본문을 복원한 새 ClientHttpResponse 반환
        return new CachedClientHttpResponse(response, body);
    }
}
