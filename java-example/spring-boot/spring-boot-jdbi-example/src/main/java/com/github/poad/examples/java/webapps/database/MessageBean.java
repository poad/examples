package com.github.poad.examples.java.webapps.database;

import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;

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
