package com.urlshortener.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShortUrlResponse {

    private String shortUrl;
}