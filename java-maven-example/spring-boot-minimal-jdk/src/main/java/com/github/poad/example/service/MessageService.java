package com.github.poad.example.service;

import com.github.poad.example.domain.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MessageService {
    List<Message> findAll();

    Optional<Message> findById(String id);

    Message create(String message);

    Optional<Message> update(String id, String message);

    void delete(String id);
}
