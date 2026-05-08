package com.urlshortener.client.dto;

import lombok.Data;

@Data
public class RateLimitResponse {

    private boolean allowed;

    private String message;
}