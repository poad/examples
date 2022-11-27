package com.github.poad.springboot.cassandra.model;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Validated
public class Artist {
    private final String id;
    private final String name;
    private final Integer age;

    public Artist(String id, @NotBlank String name, @NotNull Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

}
