package com.github.poad.examples.webauthn.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.poad.examples.webauthn.entity.User;
import com.github.poad.examples.webauthn.service.WebAuthnRegistrationService;
import com.webauthn4j.data.PublicKeyCredentialCreationOptions;
import com.webauthn4j.data.client.challenge.Challenge;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Controller
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistrationController {

    private final WebAuthnRegistrationService webAuthnService;

    public RegistrationController(WebAuthnRegistrationService webAuthnService) {
        this.webAuthnService = webAuthnService;
    }

    @Validated
    private static class AttestationOptionsParam {
        private final String email;

        // for deserialisation
        AttestationOptionsParam() {
            this(null);
        }

        AttestationOptionsParam(@NotBlank String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }
    }

    @Validated
    private static class AttestationResultParam {
        private final byte[] clientJSON;
        private final byte[] attestationObject;

        // for deserialisation
        AttestationResultParam() {
            this(null, null);
        }

        AttestationResultParam(@NotNull byte[] clientJSON, @NotNull byte[] attestationObject) {
            this.clientJSON = clientJSON;
            this.attestationObject = attestationObject;
        }
    }

    @PostMapping(value = "/attestation/options")
    public PublicKeyCredentialCreationOptions postAttestationOptions(@RequestBody AttestationOptionsParam params, HttpServletRequest httpRequest) {
        var user = webAuthnService.findOrElseCreate(params.getEmail());
        var options = webAuthnService.creationOptions(user);

        var session = httpRequest.getSession();
        session.setAttribute("attestationChallenge", options.getChallenge());
        session.setAttribute("attentionUser", user);

        return options;
    }

    @PostMapping(value = "/attestation/result")
    public void postAttestationOptions(@RequestBody AttestationResultParam params, HttpServletRequest httpRequest) throws JsonProcessingException {
        var httpSession = httpRequest.getSession();
        var challenge = (Challenge) httpSession.getAttribute("attestationChallenge");
        var user = (User) httpSession.getAttribute("attentionUser");

        webAuthnService.creationFinish(user, challenge, params.clientJSON, params.attestationObject);
    }
}
