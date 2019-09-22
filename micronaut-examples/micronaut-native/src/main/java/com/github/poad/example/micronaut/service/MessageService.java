package com.github.poad.example.micronaut.service;

import com.github.poad.example.micronaut.model.Message;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

public interface MessageService {

    List<Message> findAll();

    Optional<Message> findById(@NotBlank String id);

    Message save(@NotBlank String message);

    int update(@NotBlank String id, @NotBlank String message);

    void deleteById(@NotBlank String id);

}
