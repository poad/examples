package com.github.poad.examples.webauthn.entity;

import org.springframework.data.annotation.Immutable;

import javax.persistence.*;

@Table
@Entity
@Immutable
public class User {
    @Id
    @Column(columnDefinition="VARBINARY(64)")
    private final byte[] id;

    @Column(nullable = false)
    private final String email;

    // default constructor for JPA
    public User() {
        this(null, null);
    }

    public User(byte[] id, String email) {
        this.id = id;
        this.email = email;
    }

    public byte[] getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
