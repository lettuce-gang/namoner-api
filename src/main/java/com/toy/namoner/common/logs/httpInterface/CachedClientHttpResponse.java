package com.toy.namoner.common.logs.httpInterface;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CachedClientHttpResponse implements ClientHttpResponse {

    private final ClientHttpResponse originalResponse;
    private final byte[] body;

    public CachedClientHttpResponse(ClientHttpResponse originalResponse, byte[] body) {
        this.originalResponse = originalResponse;
        this.body = body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return originalResponse.getHeaders();
    }

    @Override
    public InputStream getBody() {
        return new ByteArrayInputStream(body); // 응답 본문 스트림 복원
    }

    @Override
    public HttpStatusCode getStatusCode() throws IOException {
        return originalResponse.getStatusCode();
    }

    @Override
    public String getStatusText() throws IOException {
        return originalResponse.getStatusText();
    }

    @Override
    public void close() {
        originalResponse.close();
    }

}
