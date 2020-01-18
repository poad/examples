package com.github.poad.examples.webauthn.controller;

import javax.servlet.http.HttpServletRequest;

class WebAuthnAttestationSession extends WebAuthnSession {
    WebAuthnAttestationSession(HttpServletRequest request) {
        super(request, "attestation");
    }
}
