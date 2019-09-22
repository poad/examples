package com.github.poad.examples.java.webapps.database;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;


@Entity(immutable = true)
@Table(name="message")
public class MessageEntity {

    @Id
    private final String id;

    private final String message;

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