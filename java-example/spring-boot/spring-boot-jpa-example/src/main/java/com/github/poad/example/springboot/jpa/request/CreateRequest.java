package com.github.poad.example.springboot.jpa.request;

import jakarta.validation.constraints.NotNull;

/**
 * Created by ken-yo on 2016/08/07.
 */
public class CreateRequest {
    private String name;
    @NotNull
    private String message;

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
