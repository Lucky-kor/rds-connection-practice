package com.codeit.dockerpractice.dto;

import java.time.LocalDateTime;

public class MessageResponse {
    private Long id;
    private String content;
    private LocalDateTime createdAt;

    public MessageResponse(Long id, String content, LocalDateTime createdAt) {
        this.id = id; this.content = content; this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
