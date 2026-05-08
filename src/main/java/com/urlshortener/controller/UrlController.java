package com.urlshortener.controller;

import com.urlshortener.dto.request.CreateShortUrlRequest;
import com.urlshortener.dto.response.ShortUrlResponse;
import com.urlshortener.entity.ShortUrl;
import com.urlshortener.service.UrlService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/urls")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    // CREATE SHORT URL
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShortUrlResponse createShortUrl(
            @Valid @RequestBody CreateShortUrlRequest request,

            @RequestHeader("clientId")
            String clientId) {

        return urlService.createShortUrl(
                request,
                clientId
        );
    }
}