package com.urlshortener.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateShortUrlRequest {

    @NotBlank
    private String url;

    // Default redirect type = 302
    private Integer redirectType = 302;

    // Optional expiry
    private Integer expiryInDays;
}