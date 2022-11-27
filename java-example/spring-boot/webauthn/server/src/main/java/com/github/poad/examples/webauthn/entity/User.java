package com.github.poad.examples.webauthn.entity;

import org.springframework.data.annotation.Immutable;

import jakarta.persistence.*;
import java.util.List;

@Table(name="user", uniqueConstraints=@UniqueConstraint(columnNames={"email", "displayName"}))
@Entity
@Immutable
public class User {
    @Id
    @Column(columnDefinition="VARBINARY(64)")
    private final byte[] id;

    @Column(nullable = false)
    private final String email;

    @Column(nullable = false)
    private final String displayName;

    @OneToMany(mappedBy="user")
    private final List<Credential> credentials;

    // default constructor for JPA
    public User() {
        this(null, null, null, null);
    }

    public User(byte[] id, String email, String displayName, List<Credential> credentials) {
        this.id = id;
        this.email = email;
        this.displayName = displayName;
        this.credentials = credentials;
    }

    public byte[] getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }
}
