package com.github.poad.examples.java.webapps.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Table("message")
public class MessageEntity implements Persistable, Serializable {


    @Column("id")
    @Id
    private String id;

    @Transient
    private boolean newInstance;

    @Column("message")
    private String message;

    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return newInstance;
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

    public void setNew(boolean newInstance) {
        this.newInstance = newInstance;
    }
}
