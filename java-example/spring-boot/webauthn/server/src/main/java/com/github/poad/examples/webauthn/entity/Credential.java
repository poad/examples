package com.github.poad.examples.webauthn.entity;

import org.springframework.data.annotation.Immutable;

import jakarta.persistence.*;

@Table
@Entity
@Immutable
public class Credential {
    @Id
    @Column(name = "credential_id", columnDefinition="VARBINARY(255)")
    private final byte[] credentialId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private final User user;

    @Lob
    @Column(nullable = false)
    private final byte[] publicKey;

    @Column(nullable = false)
    private final Long signatureCounter;

    public Credential() {
        this(null, null, null, null);
    }

    public Credential(byte[] credentialId, User user, byte[] publicKey, Long signatureCounter) {
        this.credentialId = credentialId;
        this.user = user;
        this.publicKey = publicKey;
        this.signatureCounter = signatureCounter;
    }

    public byte[] getCredentialId() {
        return credentialId;
    }

    public User getUser() {
        return user;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public Long getSignatureCounter() {
        return signatureCounter;
    }
}
