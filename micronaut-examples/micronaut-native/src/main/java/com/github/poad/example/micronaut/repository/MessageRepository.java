package com.github.poad.example.micronaut.repository;

import com.github.poad.example.micronaut.entity.MessageEntity;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

public interface MessageRepository {

    List<MessageEntity> findAll();

    Optional<MessageEntity> findById(@NotBlank String id);

    MessageEntity save(@NotBlank String message);

    int update(@NotBlank String id, @NotBlank String message);

    void deleteById(@NotBlank String id);

}
