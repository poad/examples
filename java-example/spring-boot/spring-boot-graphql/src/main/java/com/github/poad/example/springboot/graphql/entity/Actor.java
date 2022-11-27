package com.github.poad.example.springboot.graphql.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="actor")
public class Actor {
    @Id
    private String id;

    private String name;

    private String url;

    public Actor(String id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public Actor() {
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
