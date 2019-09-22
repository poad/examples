package com.github.poad.example.micronaut.model;

import javax.validation.constraints.NotNull;

public class Message {
    private String id;

    private String message;

    public Message() {
    }

    public Message(@NotNull String id, @NotNull String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
