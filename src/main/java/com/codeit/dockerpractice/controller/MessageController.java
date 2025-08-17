package com.codeit.dockerpractice.controller;

import com.codeit.dockerpractice.dto.MessageCreateRequest;
import com.codeit.dockerpractice.dto.MessageResponse;
import com.codeit.dockerpractice.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // 메시지 생성
    @PostMapping
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody MessageCreateRequest req) {
        MessageResponse saved = messageService.create(req);
        return ResponseEntity.created(URI.create("/messages/" + saved.getId())).body(saved);
    }

    // 전체 조회
    @GetMapping
    public ResponseEntity<List<MessageResponse>> findAll() {
        return ResponseEntity.ok(messageService.findAll());
    }
}
