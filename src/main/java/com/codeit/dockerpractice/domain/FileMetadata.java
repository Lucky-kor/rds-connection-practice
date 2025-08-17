package com.codeit.dockerpractice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "file_metadata")
@NoArgsConstructor
@Setter
@Getter
public class FileMetadata {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, length = 1024)
    private String s3Key;                 // 예: images/2025/08/uuid.png

    @Column(nullable = false, length = 2048)
    private String url;                   // 퍼블릭 접근 또는 사전 서명 접근용 기본 URL

    @Column(nullable = false)
    private long size;                    // 바이트 단위

    @Column(length = 255)
    private String contentType;           // image/png 등

    @Column(length = 512)
    private String originalFilename;      // 사용자가 업로드한 파일명

    @Column(nullable = false)
    private Instant uploadedAt;           // 업로드(=DB 저장) 시각

    @Column(nullable = false)
    private Instant createdAt;            // 레코드 생성 시각

    @Column(nullable = false)
    private Instant updatedAt;            // 레코드 수정 시각

    /* === JPA 라이프사이클 === */
    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        if (uploadedAt == null) uploadedAt = now;
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }

    public FileMetadata(String s3Key, String url, long size,
                        String contentType, String originalFilename, Instant uploadedAt) {
        this.s3Key = s3Key;
        this.url = url;
        this.size = size;
        this.contentType = contentType;
        this.originalFilename = originalFilename;
        this.uploadedAt = uploadedAt;
    }

    /* === 변경 메서드(예: 파일명 태그/메모 등 추가 시 확장) === */
    public void updateUrl(String url) { this.url = url; }
}
