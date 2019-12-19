package com.github.poad.examples.webauthn.entity;

import org.springframework.data.annotation.Immutable;

import javax.persistence.*;

@Table
@Entity
@Immutable
public class Credential {
    @Id
    @Column(columnDefinition="VARBINARY(255)")
    private final byte[] credentialId;

    @ManyToOne(fetch = FetchType.LAZY)
    private final User user;

    @Lob
    @Column(nullable = false)
    private final byte[] public_key;

    @Column(nullable = false)
    private final Long signature_counter;

    public Credential() {
        this(null, null, null, null);
    }

    public Credential(byte[] credentialId, User user, byte[] public_key, Long signature_counter) {
        this.credentialId = credentialId;
        this.user = user;
        this.public_key = public_key;
        this.signature_counter = signature_counter;
    }

    public byte[] getCredentialId() {
        return credentialId;
    }

    public User getUser() {
        return user;
    }

    public byte[] getPublic_key() {
        return public_key;
    }

    public Long getSignature_counter() {
        return signature_counter;
    }
}
