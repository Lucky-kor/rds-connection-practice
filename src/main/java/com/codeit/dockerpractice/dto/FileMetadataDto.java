package com.codeit.dockerpractice.dto;

import com.codeit.dockerpractice.domain.FileMetadata;

import java.time.Instant;
import java.util.UUID;

public record FileMetadataDto(
        UUID id,
        String key,
        String url,
        long size,
        String contentType,
        String originalFilename,
        Instant uploadedAt,
        Instant createdAt,
        Instant updatedAt
) {
    public static FileMetadataDto from(FileMetadata e) {
        return new FileMetadataDto(
                e.getId(),
                e.getS3Key(),
                e.getUrl(),
                e.getSize(),
                e.getContentType(),
                e.getOriginalFilename(),
                e.getUploadedAt(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }
}
