package com.github.poad.examples.java.webapps.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "message")
public class MessageEntity implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "message")
    private String message;

    // for JPA
    public MessageEntity() {
        this(null, null);
    }

    public MessageEntity(String id, String message) {
        this.id = id;
        this.message = message;
    }

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
