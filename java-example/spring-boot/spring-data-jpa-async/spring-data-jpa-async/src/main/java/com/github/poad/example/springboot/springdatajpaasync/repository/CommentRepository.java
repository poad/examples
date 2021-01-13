package com.github.poad.example.springboot.springdatajpaasync.repository;

import com.github.poad.example.springboot.springdatajpaasync.entity.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface CommentRepository extends CrudRepository<Comment, String> {
    @Async
    @Query(value="SELECT UUID()", nativeQuery = true)
    CompletableFuture<String> generateId();

    @Async
    @Query("select c from Comment c where c.id = :id")
    CompletableFuture<Comment> findByCommentId(@Param("id") String commentId);

    @Async
    @Query("select c from Comment c")
    Future<List<Comment>> findAllComments();

    @Async
    void deleteById(String id);
}
