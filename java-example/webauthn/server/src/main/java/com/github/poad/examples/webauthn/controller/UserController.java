package com.github.poad.examples.webauthn.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.poad.examples.webauthn.service.UnregisterService;
import com.github.poad.examples.webauthn.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {
    private final UserService service;
    private final UnregisterService unregisterService;

    public UserController(UserService service, UnregisterService unregisterService) {
        this.service = service;
        this.unregisterService = unregisterService;
    }

    private static class UserResponse {
        @JsonProperty("username")
        private final String username;

        // for deserialization
        private UserResponse() {
            this(null);
        }

        @JsonCreator
        private UserResponse(String username) {
            this.username = username;
        }
    }

    @GetMapping(value = "/user/{username}")
    public ResponseEntity<?> exists(@PathVariable("username") String username) {
        if (!service.existsByUsername(username)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.FOUND);
    }

    @DeleteMapping(value = "/user/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unregister(@PathVariable("username") String username, HttpServletRequest httpServletRequest) {
        unregisterService.unregister(username);
        HttpSession session = httpServletRequest.getSession(true);
        // TODO Token精製して session に 設定

    }
}
