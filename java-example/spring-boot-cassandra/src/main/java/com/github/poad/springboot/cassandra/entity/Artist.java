package com.github.poad.springboot.cassandra.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Immutable;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
@Immutable
public class Artist {
    @Id
    private final String id;
    private final String name;
    private final Integer age;

    public Artist(String id, String name, Integer age) {
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
