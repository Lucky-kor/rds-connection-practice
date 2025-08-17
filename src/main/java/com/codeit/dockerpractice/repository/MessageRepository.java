package com.codeit.dockerpractice.repository;

import com.codeit.dockerpractice.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
