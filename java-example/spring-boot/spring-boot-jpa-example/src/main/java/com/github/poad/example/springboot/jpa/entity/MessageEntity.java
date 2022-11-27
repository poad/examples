package com.github.poad.example.springboot.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

/**
 * Created by ken-yo on 2016/08/06.
 */
@Entity
@Table(name = "message")
@PrimaryKeyJoinColumn(name = "id")
public class MessageEntity extends Identifier {
    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "message")
    private String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
