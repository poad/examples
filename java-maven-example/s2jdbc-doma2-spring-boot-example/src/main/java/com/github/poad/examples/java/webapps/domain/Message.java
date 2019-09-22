package com.github.poad.examples.java.webapps.domain;

import org.springframework.data.annotation.Immutable;

import java.io.Serializable;

@Immutable
public class Message implements Serializable {
    private final String id;
    private final String message;

    public Message() {
        this(null, null);
    }

    public Message(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
