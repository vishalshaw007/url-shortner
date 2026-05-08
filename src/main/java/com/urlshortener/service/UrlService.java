package com.urlshortener.service;

import com.urlshortener.dto.request.CreateShortUrlRequest;
import com.urlshortener.dto.response.ShortUrlResponse;
import com.urlshortener.entity.ShortUrl;

public interface UrlService {

	ShortUrlResponse createShortUrl(
	        CreateShortUrlRequest request,
	        String clientId
	);
    ShortUrl getShortUrl(String shortCode);
}