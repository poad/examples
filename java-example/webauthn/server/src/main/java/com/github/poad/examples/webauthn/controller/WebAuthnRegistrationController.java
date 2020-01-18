package com.github.poad.examples.webauthn.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.poad.examples.webauthn.service.WebAuthnRegistrationService;
import com.webauthn4j.data.PublicKeyCredentialCreationOptions;
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
import javax.validation.constraints.Size;

@Controller
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class WebAuthnRegistrationController {

    private final WebAuthnRegistrationService webAuthnService;

    public WebAuthnRegistrationController(WebAuthnRegistrationService webAuthnService) {
        this.webAuthnService = webAuthnService;
    }

    private static class AttestationOptionsParam {
        private final String email;
        private final String displayName;


        // for deserialization
        AttestationOptionsParam() {
            this(null, null);
        }

        AttestationOptionsParam(@NotNull @Size(min = 1) @NotBlank String email, @NotNull @Size(min = 1) @NotBlank String displayName) {
            this.email = email;
            this.displayName = displayName;
        }

        public String getEmail() {
            return email;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    private static class AttestationResultParam {
        @JsonProperty("clientDataJSON")
        private final byte[] clientDataJSON;
        @JsonProperty("attestationObject")
        private final byte[] attestationObject;
        @JsonProperty("clientExtensionsJSON")
        private final String clientExtensionsJSON;

        // for deserialization
        AttestationResultParam() {
            this(null, null, null);
        }

        @JsonCreator
        AttestationResultParam(@NotNull @Size(min = 1) byte[] clientDataJSON, @NotNull @Size(min = 1) byte[] attestationObject, String clientExtensionsJSON) {
            this.clientDataJSON = clientDataJSON;
            this.attestationObject = attestationObject;
            this.clientExtensionsJSON = clientExtensionsJSON;
        }
    }

    @PostMapping(value = "/attestation/options")
    public PublicKeyCredentialCreationOptions postAttestationOptions(@RequestBody AttestationOptionsParam params, HttpServletRequest httpRequest) {
        var user = webAuthnService.findOrElseCreate(params.getEmail(), params.getDisplayName());
        var options = webAuthnService.creationOptions(user);

        new WebAuthnAttestationSession(httpRequest)
                .setChallenge(options.getChallenge())
                .setUser(user);

        return options;
    }

    @PostMapping(value = "/attestation/result")
    public void postAttestationOptions(@RequestBody AttestationResultParam params, HttpServletRequest httpRequest) {
        var session = new WebAuthnAttestationSession(httpRequest);
        var challenge = session.getChallenge().orElseThrow();
        var user = session.getUser().orElseThrow();

        webAuthnService.creationFinish(user, challenge, params.clientDataJSON, params.attestationObject, params.clientExtensionsJSON);
    }
}
