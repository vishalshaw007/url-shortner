package com.urlshortener.controller;

import com.urlshortener.entity.ShortUrl;
import com.urlshortener.service.UrlService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class RedirectController {

    private final UrlService urlService;

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(
            @PathVariable String shortCode) {

        ShortUrl shortUrl =
                urlService.getShortUrl(shortCode);

        HttpStatus status =
                shortUrl.getRedirectType() == 301
                        ? HttpStatus.MOVED_PERMANENTLY
                        : HttpStatus.FOUND;

        return ResponseEntity.status(status)
                .location(URI.create(shortUrl.getOriginalUrl()))
                .build();
    }
}