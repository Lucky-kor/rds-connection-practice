package com.codeit.dockerpractice.service;

import com.codeit.dockerpractice.domain.FileMetadata;
import com.codeit.dockerpractice.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileMetadataService {

    private final S3UploadService s3;
    private final FileMetadataRepository repository;

    /**
     * ✅ 업로드 + DB 저장 (원자적 유스케이스)
     */
    @Transactional
    public FileMetadata uploadAndSave(MultipartFile file) {
        S3UploadService.UploadedMeta meta = s3.uploadAndReturn(file);

        FileMetadata entity = new FileMetadata(
                meta.getKey(),
                meta.getUrl(),
                meta.getSize(),
                meta.getContentType(),
                meta.getOriginalFilename(),
                meta.getUploadedAt()
        );
        return repository.save(entity);
    }

    /** 목록 조회 (최신 50건) */
    @Transactional(readOnly = true)
    public List<FileMetadata> listLatest() {
        return repository.findTop50ByOrderByCreatedAtDesc();
    }

    /** 접두사(prefix)로 조회 */
    @Transactional(readOnly = true)
    public List<FileMetadata> listByPrefix(String prefix) {
        return repository.findByS3KeyStartingWithOrderByCreatedAtDesc(prefix);
    }

    /** 단건 조회 */
    @Transactional(readOnly = true)
    public FileMetadata findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("File not found: " + id));
    }

    /** 삭제 (DB 레코드 + 선택적으로 S3 오브젝트) */
    @Transactional
    public void delete(UUID id) {
        FileMetadata found = findById(id);
        repository.delete(found);
        // ⚠️ 필요 시 보상 로직: S3에서 found.getS3Key() 삭제 (Bucket 정책 고려)
    }

    /** URL 재생성(예: 버킷/리전 변경 시), 단순 예시 */
    @Transactional
    public FileMetadata refreshUrl(UUID id) {
        FileMetadata found = findById(id);
        String refreshed = s3.toPublicUrl(found.getS3Key());
        found.updateUrl(refreshed);
        return found; // dirty checking으로 update
    }
}
