package com.github.poad.example.springboot.springdatajpaasync.entity;

import org.springframework.data.annotation.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
