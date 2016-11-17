package org.bitbucket.poad1010.example.springboot.batch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

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
