package com.github.poad.examples.webauthn.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.poad.examples.webauthn.service.SignInService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class SignInController {

    private final SignInService service;

    public SignInController(SignInService service) {
        this.service = service;
    }

    private static class SignInRequest {
        @JsonProperty("username")
        private final String username;

        @JsonProperty("password")
        private final String password;

        // for deserialization
        private SignInRequest() {
            this(null, null);
        }

        @JsonCreator
        private SignInRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    @PostMapping(value = "/signin")
    @ResponseStatus(HttpStatus.CREATED)
    public void signIn(@RequestBody SignInRequest request, HttpServletRequest httpServletRequest) {
        service.signIn(request.username);
        HttpSession session = httpServletRequest.getSession(true);
        // TODO Token精製して session に 設定
    }
}
