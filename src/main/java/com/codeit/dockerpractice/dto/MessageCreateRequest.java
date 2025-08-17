package com.codeit.dockerpractice.dto;

import jakarta.validation.constraints.NotBlank;

public class MessageCreateRequest {
    @NotBlank(message = "content는 비어 있을 수 없습니다.")
    private String content;

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
