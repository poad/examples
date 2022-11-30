package com.github.poad.example.springboot.springdatajpaasync.service;

import com.github.poad.example.springboot.springdatajpaasync.entity.Comment;
import com.github.poad.example.springboot.springdatajpaasync.repository.CommentRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class CommentService {
    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public List<Comment> list() {
        Future<List<Comment>> future = repository.findAllComments();

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public Optional<Comment> get(String id) {
        Future<Comment> future = repository.findByCommentId(id);
        try {
            return Optional.ofNullable(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Transactional
    public Comment add(String comment) throws ExecutionException, InterruptedException {
        return repository.save(new Comment(repository.generateId().get(), comment));
    }

    @Transactional
    public Comment update(String id, String comment) {
        return repository.save(new Comment(id, comment));
    }

    public void delete(String id) {
        repository.findById(id).ifPresent(repository::delete);
    }
}
