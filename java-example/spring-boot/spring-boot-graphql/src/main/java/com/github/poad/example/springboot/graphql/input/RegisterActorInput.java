package com.github.poad.example.springboot.graphql.input;

public class RegisterActorInput {
    private String name;

    private String url;

    public RegisterActorInput(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
