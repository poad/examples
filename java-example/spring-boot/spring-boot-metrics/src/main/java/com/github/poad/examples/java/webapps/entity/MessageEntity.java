package com.github.poad.examples.java.webapps.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="message")
public class MessageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private final String id;

    private final String message;

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

    public String getMessage() {
        return message;
    }
}
