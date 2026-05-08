package com.urlshortener.client;

import com.urlshortener.client.dto.RateLimitRequest;
import com.urlshortener.client.dto.RateLimitResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class RateLimiterClient {

    private final RestTemplate restTemplate;

    private static final String RATE_LIMITER_URL =
            "http://localhost:8080/api/v1/rate-limit/check";

    public boolean isAllowed(String clientId, String api) {

        RateLimitRequest request =
                RateLimitRequest.builder()
                        .clientId(clientId)
                        .api(api)
                        .build();

        RateLimitResponse response =
                restTemplate.postForObject(
                        RATE_LIMITER_URL,
                        request,
                        RateLimitResponse.class
                );

        return response != null && response.isAllowed();
    }
}