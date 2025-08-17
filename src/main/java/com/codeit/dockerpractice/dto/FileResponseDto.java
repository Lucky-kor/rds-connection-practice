package com.codeit.dockerpractice.dto;

import java.time.Instant;

public record FileResponseDto(
        String key,
        String url,
        long size,
        Instant lastModified
) {}