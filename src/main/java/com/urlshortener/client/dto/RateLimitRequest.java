package com.urlshortener.client.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RateLimitRequest {

    private String clientId;

    private String api;
}