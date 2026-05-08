package com.urlshortener.util;

import com.urlshortener.repository.ShortUrlRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class ShortCodeGenerator {

    private final ShortUrlRepository repository;

    private static final long PRIME = 999983;

    public String generate(Long id) {

        String shortCode;

        do {

            long randomSalt =
                    ThreadLocalRandom.current()
                            .nextLong(1000, 9999);

            long transformed =
                    (id * PRIME) + randomSalt;

            shortCode =
                    Base62Util.encode(transformed);

        } while (repository.existsByShortCode(shortCode));

        return shortCode;
    }
}