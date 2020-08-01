package com.github.poad.model;

public class Message {
    private final String id;
    private final String content;

    // for JPA
    public Message() {
        this(null, null);
    }

    public Message(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
