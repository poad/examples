package com.github.poad.examples.webauthn.entity;

import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import java.util.List;

@Table(name="webauthn_user")
@Entity
@Immutable
public class WebAuthnUser {
    @Id
    @Column(columnDefinition="VARBINARY(64)")
    private final byte[] id;

    @Column(nullable = false)
    private final String email;

    @Column(nullable = false)
    private final String displayName;

    @OneToMany(mappedBy="user")
    private final List<WebAuthnCredential> credentials;

    // default constructor for JPA
    public WebAuthnUser() {
        this(null, null, null, null);
    }

    public WebAuthnUser(byte[] id, String email, String displayName, List<WebAuthnCredential> credentials) {
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
