package com.github.poad.examples.java.webapps.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "message")
public class MessageEntity implements Persistable, Serializable {

    @Column(name = "id")
    @Id
    private String id;

    @Column(name = "message")
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