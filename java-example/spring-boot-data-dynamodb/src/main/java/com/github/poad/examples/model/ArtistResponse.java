package com.github.poad.examples.model;

public class ArtistResponse {
    private final String name;
    private final int age;

    public ArtistResponse(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
