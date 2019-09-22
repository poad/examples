package com.github.poad.examples.java.webapps.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Table("message")
public class MessageEntity implements Persistable, Serializable {

    @Column("id")
    @Id
    private String id;

    @Column("message")
    private String message;

    @Transient
    private final boolean newInstance;

    // 自動生成されるRepositoryでfindする際に必要
    public MessageEntity() {
        this(null, null, false);
    }

    public MessageEntity(String id, String message) {
        this(id, message, false);
    }

    public MessageEntity(String id, String message, boolean newInstance) {
        this.id = id;
        this.message = message;
        this.newInstance = newInstance;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean isNew() {
        return newInstance;
    }
}