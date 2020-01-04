package com.github.poad.examples.webauthn.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.poad.examples.webauthn.service.LoginUserDetail;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {
    private static class EchoRequest {
        @JsonProperty("message")
        private final String message;

        private EchoRequest() {
            this(null);
        }

        @JsonCreator
        private EchoRequest(String message) {
            this.message = message;
        }
    }

    private static class EchoResponse {
        @JsonProperty("username")
        private final String username;

        @JsonProperty("message")
        private final String message;

        private EchoResponse() {
            this(null, null);
        }

        @JsonCreator
        private EchoResponse(String username, String message) {
            this.username = username;
            this.message = message;
        }
    }

    @PostMapping(value = "/echo")
    public EchoResponse echo(@RequestBody EchoRequest request, @AuthenticationPrincipal LoginUserDetail userDetail) {
        return new EchoResponse(userDetail.getUsername(), request.message);
    }

}
