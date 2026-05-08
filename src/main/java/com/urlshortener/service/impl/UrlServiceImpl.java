package com.urlshortener.service.impl;

import com.urlshortener.cache.UrlCacheService;
import com.urlshortener.client.RateLimiterClient;
import com.urlshortener.dto.request.CreateShortUrlRequest;
import com.urlshortener.dto.response.ShortUrlResponse;
import com.urlshortener.entity.ShortUrl;
import com.urlshortener.exception.RateLimitExceededException;
import com.urlshortener.exception.ShortUrlNotFoundException;
import com.urlshortener.repository.ShortUrlRepository;
import com.urlshortener.service.UrlService;
import com.urlshortener.util.ShortCodeGenerator;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final ShortUrlRepository repository;

    private final ShortCodeGenerator generator;
    
    private final UrlCacheService cacheService;
    
    private final RateLimiterClient rateLimiterClient;

    @Value("${app.base-url}")
    private String baseUrl;

    @Override
    public ShortUrlResponse createShortUrl(
            CreateShortUrlRequest request,
            String clientId) {

        // Step 1 -> Check rate limit
        boolean allowed =
                rateLimiterClient.isAllowed(
                        clientId,
                        "/api/v1/urls"
                );

        if (!allowed) {
            throw new RateLimitExceededException(
                    "Rate limit exceeded"
            );
        }

        // Step 2 -> Create entity
        ShortUrl shortUrl = ShortUrl.builder()
                .originalUrl(request.getUrl())
                .clickCount(0L)
                .createdAt(LocalDateTime.now())

                // Redirect type
                .redirectType(
                        request.getRedirectType() != null
                                ? request.getRedirectType()
                                : 302
                )

                // Expiry support
                .expiryAt(
                        request.getExpiryInDays() != null
                                ? LocalDateTime.now()
                                .plusDays(request.getExpiryInDays())
                                : null
                )

                .build();

        // Step 3 -> Save first time to generate DB ID
        shortUrl = repository.save(shortUrl);

        // Step 4 -> Generate short code
        String shortCode =
                generator.generate(shortUrl.getId());

        // Step 5 -> Update entity
        shortUrl.setShortCode(shortCode);

        // Step 6 -> Save updated entity
        shortUrl = repository.save(shortUrl);

        // Step 7 -> Cache in Redis
        cacheService.save(shortCode, shortUrl);

        // Step 8 -> Return response
        return ShortUrlResponse.builder()
                .shortUrl(baseUrl + "/" + shortCode)
                .build();
    }
    @Override
    public ShortUrl getShortUrl(String shortCode) {

        // Step 1 -> Check Redis cache
        ShortUrl cachedUrl =
                cacheService.get(shortCode);

        if (cachedUrl != null) {

            // Increment analytics
            cachedUrl.setClickCount(
                    cachedUrl.getClickCount() + 1
            );

            repository.save(cachedUrl);

            return cachedUrl;
        }

        // Step 2 -> Fetch from DB
        ShortUrl shortUrl =
                repository.findByShortCode(shortCode)
                        .orElseThrow(() ->
                                new ShortUrlNotFoundException(
                                        "Short URL not found"));

        // Step 3 -> Expiry validation
        if (shortUrl.getExpiryAt() != null &&
                shortUrl.getExpiryAt()
                        .isBefore(LocalDateTime.now())) {

            throw new RuntimeException("URL expired");
        }

        // Step 4 -> Increment analytics
        shortUrl.setClickCount(
                shortUrl.getClickCount() + 1
        );

        repository.save(shortUrl);

        // Step 5 -> Save in Redis
        cacheService.save(shortCode, shortUrl);

        return shortUrl;
    }
}