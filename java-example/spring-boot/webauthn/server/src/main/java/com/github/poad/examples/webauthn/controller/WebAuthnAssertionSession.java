package com.github.poad.examples.webauthn.controller;

import jakarta.servlet.http.HttpServletRequest;

public class WebAuthnAssertionSession extends WebAuthnSession {
    WebAuthnAssertionSession(HttpServletRequest request) {
        super(request, "assertion");
    }
}
