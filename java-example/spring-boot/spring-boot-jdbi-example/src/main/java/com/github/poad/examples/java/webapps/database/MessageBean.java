package com.github.poad.examples.java.webapps.database;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@IdClass(String.class)
@Entity
@Table(name="message")
public class MessageBean {

    @Id
    @Column(name="id")
    private String id;

    @Column(name="message")
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
