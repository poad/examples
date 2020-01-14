package com.github.poad.examples.webauthn.controller;

import com.webauthn4j.authenticator.Authenticator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;

public class WebAuthnAuthSession extends WebAuthnSession {

    private final BCryptPasswordEncoder encoder;

    WebAuthnAuthSession(HttpServletRequest request) {
        super(request, "authentication");
        this.encoder = new BCryptPasswordEncoder();
    }

    public WebAuthnAuthSession setAuthenticator(Authenticator authenticator) {
        return this;
    }
}
