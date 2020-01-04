package com.github.poad.examples.webauthn.entity;

import org.springframework.data.annotation.Immutable;

import javax.persistence.*;

@Table(name="users")
@Entity
@Immutable
public class User {
    @Id
    private final String username;
    private final String encodedPassword;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private final WebAuthnUser webAuthnUser;

    public User() {
        this(null, null, false, false, false, false, null);
    }

    public User(
            String username,
            String encodedPassword,
            boolean accountNonExpired,
            boolean accountNonLocked,
            boolean credentialsNonExpired,
            boolean enabled,
            WebAuthnUser webAuthnUser) {
        this.username = username;
        this.encodedPassword = encodedPassword;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.webAuthnUser = webAuthnUser;
    }

    public String getUsername() {
        return username;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public WebAuthnUser getWebAuthnUser() {
        return webAuthnUser;
    }
}
