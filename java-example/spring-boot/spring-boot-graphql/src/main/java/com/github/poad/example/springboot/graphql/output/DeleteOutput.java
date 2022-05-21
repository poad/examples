package com.github.poad.example.springboot.graphql.output;

import org.springframework.data.annotation.Immutable;

@Immutable
public class DeleteOutput {
    private final boolean success;


    public DeleteOutput(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
