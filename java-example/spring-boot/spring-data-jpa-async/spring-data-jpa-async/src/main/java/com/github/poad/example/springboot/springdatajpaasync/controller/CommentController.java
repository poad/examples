package com.github.poad.example.springboot.springdatajpaasync.controller;

import com.github.poad.example.springboot.springdatajpaasync.entity.Comment;
import com.github.poad.example.springboot.springdatajpaasync.service.CommentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@Valid
public class CommentController {
    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @Valid
    public static class CommentModel {

        private final String id;
        @NotNull
        @NotBlank
        private final String comment;

        public CommentModel(String id, @NotNull @NotBlank String comment) {
            this.id = id;
            this.comment = comment;
        }

        @SuppressWarnings("unused")
        public String getId() {
            return id;
        }

        @SuppressWarnings("unused")
        public String getComment() {
            return comment;
        }
    }

    @GetMapping("/")
    public List<CommentModel> list() {
        return service.list().stream()
                .map(comment -> new CommentModel(comment.getId(), comment.getComment()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Optional<CommentModel> get(@PathVariable("id") @NotNull @NotBlank String id) {
        return service.get(id).map(comment -> new CommentModel(comment.getId(), comment.getComment()));
    }

    @PostMapping(path = "/", consumes= MediaType.APPLICATION_JSON_VALUE)
    public CommentModel add(@RequestBody @NotNull CommentModel request) throws ExecutionException, InterruptedException {
        Comment entity = service.add(request.comment);
        return new CommentModel(entity.getId(), entity.getComment());
    }

    @PutMapping(path = "/{id}", consumes= MediaType.APPLICATION_JSON_VALUE)
    public CommentModel update(@PathVariable("id") @NotNull @NotBlank String id, @RequestBody @NotNull CommentModel request) throws ExecutionException, InterruptedException {
        Comment entity = service.update(id, request.comment);
        return new CommentModel(entity.getId(), entity.getComment());
    }

    @DeleteMapping(path = "/{id}")
    public void update(@PathVariable("id") @NotNull @NotBlank String id) {
        service.delete(id);
    }
}
