package com.codeit.dockerpractice.repository;

import com.codeit.dockerpractice.domain.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, UUID> {
    Optional<FileMetadata> findByS3Key(String s3Key);
    List<FileMetadata> findTop50ByOrderByCreatedAtDesc();
    List<FileMetadata> findByS3KeyStartingWithOrderByCreatedAtDesc(String prefix);
}
