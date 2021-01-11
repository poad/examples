package com.github.poad.examples.java.webapps.domain;

import org.springframework.data.annotation.Immutable;

@Immutable
public class Message {

    private String id;

    private String message;

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
