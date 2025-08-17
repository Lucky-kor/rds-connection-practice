package com.codeit.dockerpractice.controller;

import com.codeit.dockerpractice.dto.FileMetadataDto;
import com.codeit.dockerpractice.dto.FileResponseDto;
import com.codeit.dockerpractice.service.FileMetadataService;
import com.codeit.dockerpractice.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.net.URI;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class S3UploadController {

    private final S3UploadService s3;
    private final FileMetadataService metaService;   // ✅ 추가 주입

    @PostMapping("/upload")
    public ResponseEntity<FileMetadataDto> upload(@RequestParam("file") MultipartFile file) {
        var saved = metaService.uploadAndSave(file); // ✅ 업로드 + DB 저장
        var dto = FileMetadataDto.from(saved);
        return ResponseEntity
                .created(URI.create("/files/db/" + saved.getId()))
                .body(dto);
    }

//    @PostMapping("/upload")
//    public ResponseEntity<Void> upload(@RequestParam("file") MultipartFile file) {
//        String url = s3.store(file);
//        return ResponseEntity.created(URI.create(url)).build();
//    }
}