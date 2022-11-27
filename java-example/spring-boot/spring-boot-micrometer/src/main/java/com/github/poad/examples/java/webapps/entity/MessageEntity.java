package com.github.poad.examples.java.webapps.entity;


import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Immutable
@Entity
@Table(name="message")
public class MessageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private final UUID id;

    private final String message;

    public MessageEntity() {
        this(null, null);
    }

    public MessageEntity(UUID id, String message) {
        this.id = id;
        this.message = message;
    }

    public UUID getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
