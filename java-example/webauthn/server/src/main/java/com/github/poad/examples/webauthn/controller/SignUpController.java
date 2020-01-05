package com.github.poad.examples.webauthn.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.poad.examples.webauthn.exception.UserAlreadyExistException;
import com.github.poad.examples.webauthn.service.SignUpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class SignUpController {

    private final SignUpService service;

    public SignUpController(SignUpService service) {
        this.service = service;
    }

    private static class SignUpRequest {
        @JsonProperty("username")
        private final String username;

        @JsonProperty("password")
        private final String password;

        // for deserialization
        private SignUpRequest() {
            this(null, null);
        }

        @JsonCreator
        private SignUpRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    @PostMapping(value = "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody SignUpRequest request, HttpServletRequest httpServletRequest) {
        service.signUp(request.username, request.password);
        HttpSession session = httpServletRequest.getSession(true);
        // TODO Token精製して session に 設定

    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> alreadyExist(UserAlreadyExistException ex) {
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
