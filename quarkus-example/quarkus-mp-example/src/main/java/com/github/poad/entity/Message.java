package com.github.poad.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity
@Table(name="message", schema = "test")
@Immutable
public class Message {
    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",strategy = "uuid")
    private final String id;
    @Column(name="content", nullable = false, unique = true)
    private final String content;

    // for JPA
    public Message() {
        this(null);
    }

    public Message(String content) {
        this(null, content);
    }

    public Message(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
