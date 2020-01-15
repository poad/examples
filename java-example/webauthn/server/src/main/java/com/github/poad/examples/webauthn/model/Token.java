package com.github.poad.examples.webauthn.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {
    @JsonProperty("token")
    private final String token;

    public Token() {
        this(null);
    }

    @JsonCreator
    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
