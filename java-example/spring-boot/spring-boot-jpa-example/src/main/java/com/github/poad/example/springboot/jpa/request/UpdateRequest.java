package com.github.poad.example.springboot.jpa.request;

import jakarta.validation.constraints.NotNull;

/**
 * Created by ken-yo on 2016/08/07.
 */
public class UpdateRequest {
    @NotNull
    private String id;
    private String name;
    @NotNull
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
