package com.urlshortener.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "short_urls")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShortUrl implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String shortCode;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String originalUrl;

    private Long clickCount;

    private LocalDateTime createdAt;

    private LocalDateTime expiryAt;

    // 301 or 302
    private Integer redirectType;
}