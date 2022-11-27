package com.github.poad.examples.webauthn.controller;

import com.github.poad.examples.webauthn.entity.User;
import com.webauthn4j.data.client.challenge.Challenge;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

public abstract class WebAuthnSession {
    private final HttpSession session;
    private final String prefix;

    WebAuthnSession(HttpServletRequest request, String prefix) {
        this.session = request.getSession();
        this.prefix = prefix;
    }

    Optional<Challenge> getChallenge() {
        return Optional.ofNullable((Challenge) session.getAttribute(prefix + "Challenge"));
    }

    Optional<User> getUser() {
        return Optional.ofNullable((User) session.getAttribute(prefix + "User"));
    }

    Optional<String> getJws() {
        return Optional.ofNullable((String) session.getAttribute(prefix + "Token"));
    }

    WebAuthnSession setChallenge(Challenge challenge) {
        session.setAttribute(prefix + "Challenge", challenge);
        return this;
    }

    WebAuthnSession setUser(User user) {
        session.setAttribute(prefix + "User", user);
        return this;
    }
    WebAuthnSession removeChallenge() {
        session.removeAttribute(prefix + "Challenge");
        return this;
    }

    WebAuthnSession setJws(String jws) {
        session.setAttribute(prefix + "Token", jws);
        return this;
    }
}
