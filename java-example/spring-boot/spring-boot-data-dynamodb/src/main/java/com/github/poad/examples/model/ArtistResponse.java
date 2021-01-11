package com.github.poad.examples.model;

public class ArtistResponse {
    private final String name;
    private final int age;
    private final String sex;

    public ArtistResponse(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }
}
