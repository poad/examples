package com.github.poad.examples.webauthn.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.poad.examples.webauthn.service.WebAuthnAuthenticationService;
import com.webauthn4j.data.PublicKeyCredentialRequestOptions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class WebAuthnAuthenticationController {

    private final WebAuthnAuthenticationService webAuthnService;

    public WebAuthnAuthenticationController(WebAuthnAuthenticationService webAuthnService) {
        this.webAuthnService = webAuthnService;
    }

    // POST /assertion/options のJSONパラメータ
    private static class AssertionOptionsParam {
        @JsonProperty("email")
        private final String email;

        // for deserialization
        private AssertionOptionsParam() {
            this(null);
        }

        @JsonCreator
        private AssertionOptionsParam(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }
    }

    // POST /assertion/options のエンドポイントを設定
    @PostMapping(value = "/assertion/options")
    public PublicKeyCredentialRequestOptions postAssertionOptions(
            @RequestBody AssertionOptionsParam params,
            HttpServletRequest httpRequest) {

        var user = webAuthnService.find(params.getEmail()).orElse(null);
        var options = webAuthnService.requestOptions(user);

        // challengeをHTTPセッションに一時保存
        new WebAuthnAssertionSession(httpRequest)
                .setChallenge(options.getChallenge());

        return options;
    }

    // POST /assertion/result のJSONパラメータ
    private static class AuthenticationResultParam {
        @JsonProperty("credentialId")
        private final byte[] credentialId;
        @JsonProperty("clientDataJSON")
        private final byte[] clientDataJSON;
        @JsonProperty("authenticatorData")
        private final byte[] authenticatorData;
        @JsonProperty("signature")
        private final byte[] signature;
        @JsonProperty("userHandle")
        private final byte[] userHandle;
        @JsonProperty("clientExtensionsJSON")
        private final String clientExtensionsJSON;

        // for deserialization
        private AuthenticationResultParam() {
            this(null, null, null, null, null, null);
        }

        @JsonCreator
        private AuthenticationResultParam(byte[] credentialId, byte[] clientDataJSON, byte[] authenticatorData, byte[] signature, byte[] userHandle, String clientExtensionsJSON) {
            this.credentialId = credentialId;
            this.clientDataJSON = clientDataJSON;
            this.authenticatorData = authenticatorData;
            this.signature = signature;
            this.userHandle = userHandle;
            this.clientExtensionsJSON = clientExtensionsJSON;
        }
    }


    // POST /assertion/result のエンドポイント
    @PostMapping(value = "/assertion/result")
    public void postAssertionResult(
            @RequestBody AuthenticationResultParam params,
            HttpServletRequest httpRequest) {

        // HTTPセッションからchallengeを取得
        var session = new WebAuthnAssertionSession(httpRequest);
        var challenge = session.getChallenge().orElseThrow();

        // 署名の検証
        var user = webAuthnService.assertionFinish(
                challenge,
                params.credentialId,
                params.userHandle,
                params.authenticatorData,
                params.clientDataJSON,
                params.clientExtensionsJSON,
                params.signature);
        var name = user.getDisplayName();
    }

}
