package com.github.poad.example.springboot.springdatajpaasync.entity;

import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
@Immutable
public class Comment {
    @Id
    private final String id;

    @Column
    private final String comment;

    // for JPA
    public Comment() {
        this(null, null);
    }

    public Comment(String id, String comment) {
        this.id = id;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }
}
