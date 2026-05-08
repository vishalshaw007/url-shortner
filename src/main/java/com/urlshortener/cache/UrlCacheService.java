package com.urlshortener.cache;

import com.urlshortener.entity.ShortUrl;

public interface UrlCacheService {

    ShortUrl get(String shortCode);

    void save(String shortCode, ShortUrl shortUrl);
}