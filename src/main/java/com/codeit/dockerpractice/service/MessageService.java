package com.codeit.dockerpractice.service;

import com.codeit.dockerpractice.domain.Message;
import com.codeit.dockerpractice.dto.MessageCreateRequest;
import com.codeit.dockerpractice.dto.MessageResponse;
import com.codeit.dockerpractice.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Transactional
    public MessageResponse create(MessageCreateRequest req) {
        Message saved = messageRepository.save(new Message(req.getContent()));
        return new MessageResponse(saved.getId(), saved.getContent(), saved.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public List<MessageResponse> findAll() {
        return messageRepository.findAll().stream()
                .map(m -> new MessageResponse(m.getId(), m.getContent(), m.getCreatedAt()))
                .toList();
    }
}
