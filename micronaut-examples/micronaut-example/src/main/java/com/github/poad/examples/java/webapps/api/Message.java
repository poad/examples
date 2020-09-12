package com.github.poad.examples.java.webapps.api;

import javax.validation.constraints.NotBlank;

public class Message {
    private final String id;
    @NotBlank
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
