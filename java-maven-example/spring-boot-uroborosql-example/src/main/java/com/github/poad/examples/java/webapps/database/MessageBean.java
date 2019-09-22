package com.github.poad.examples.java.webapps.database;

import jp.co.future.uroborosql.mapping.annotations.Table;

@Table(name = "message")
public class MessageBean {

    private String id;

    private String message;

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
