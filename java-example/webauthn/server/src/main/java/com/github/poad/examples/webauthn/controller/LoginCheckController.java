package com.github.poad.examples.webauthn.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class LoginCheckController {

    private static class LoginStatus {
        @JsonProperty("status")
        private final boolean status;

        // for deserialization
        private LoginStatus() {
            this(false);
        }

        @JsonCreator
        private LoginStatus(boolean status) {
            this.status = status;
        }

        public boolean isStatus() {
            return status;
        }
    }

    @GetMapping("/login")
    public LoginStatus loginStatus(HttpServletRequest request) {
        var key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        var session = new WebAuthnAuthSession(request);
        return new LoginStatus(Optional.ofNullable(request.getHeader("Authorization"))
                .filter(header -> header.startsWith("Bearer "))
                .map(header -> header.substring("Bearer ".length()))
                .flatMap(jws -> session.getJws().map(expected -> expected.equals(jws)))
                .orElse(Boolean.FALSE));
    }
}
