package com.urlshortener.cache.impl;

import com.urlshortener.cache.UrlCacheService;
import com.urlshortener.entity.ShortUrl;

import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UrlCacheServiceImpl
        implements UrlCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String PREFIX = "short:url:";

    @Override
    public ShortUrl get(String shortCode) {

        Object value =
                redisTemplate.opsForValue()
                        .get(PREFIX + shortCode);

        if (value == null) {
            return null;
        }

        return (ShortUrl) value;
    }

    @Override
    public void save(String shortCode, ShortUrl shortUrl) {

        redisTemplate.opsForValue()
                .set(
                        PREFIX + shortCode,
                        shortUrl,
                        Duration.ofHours(24)
                );
    }
}