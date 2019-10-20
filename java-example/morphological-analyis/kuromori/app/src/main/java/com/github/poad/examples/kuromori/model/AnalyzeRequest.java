package com.github.poad.examples.kuromori.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class AnalyzeRequest implements Serializable {
    @NotNull
    @NotBlank
    private final String text;

    public AnalyzeRequest() {
        this(null);
    }

    public AnalyzeRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
