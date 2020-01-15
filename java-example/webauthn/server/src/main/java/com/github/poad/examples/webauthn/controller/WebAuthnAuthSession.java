package com.github.poad.examples.webauthn.controller;

import javax.servlet.http.HttpServletRequest;

public class WebAuthnAuthSession extends WebAuthnSession {

    WebAuthnAuthSession(HttpServletRequest request) {
        super(request, "authentication");
    }
}
