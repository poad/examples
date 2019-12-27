package com.github.poad.examples.webauthn.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.poad.examples.webauthn.service.WebAuthnAuthenticationService;
import com.webauthn4j.data.PublicKeyCredentialRequestOptions;
import com.webauthn4j.data.client.challenge.Challenge;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthenticationController {

    private final WebAuthnAuthenticationService webAuthnService;

    public AuthenticationController(WebAuthnAuthenticationService webAuthnService) {
        this.webAuthnService = webAuthnService;
    }

    // POST /assertion/options のJSONパラメータ
    private static class AssertionOptionsParam {
        @JsonProperty("email")
        private final String email;

        // for deserialisation
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
        var session = httpRequest.getSession();
        session.setAttribute("assertionChallenge", options.getChallenge());

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

        // for deserialisation
        private AuthenticationResultParam() {
            this(null, null, null, null, null);
        }

        @JsonCreator
        private AuthenticationResultParam(byte[] credentialId, byte[] clientDataJSON, byte[] authenticatorData, byte[] signature, byte[] userHandle) {
            this.credentialId = credentialId;
            this.clientDataJSON = clientDataJSON;
            this.authenticatorData = authenticatorData;
            this.signature = signature;
            this.userHandle = userHandle;
        }

        public byte[] getCredentialId() {
            return credentialId;
        }

        public byte[] getClientDataJSON() {
            return clientDataJSON;
        }

        public byte[] getAuthenticatorData() {
            return authenticatorData;
        }

        public byte[] getSignature() {
            return signature;
        }

        public byte[] getUserHandle() {
            return userHandle;
        }
    }

    // POST /assertion/result のエンドポイント
    @PostMapping(value = "/assertion/result")
    public void postAssertionResult(
            @RequestBody AuthenticationResultParam params,
            HttpServletRequest httpRequest) {

        // HTTPセッションからchallengeを取得
        var httpSession = httpRequest.getSession();
        var challenge = (Challenge) httpSession.getAttribute("assertionChallenge");

        // ※サンプルコードでは、HTTPセッションからchallengeを削除
        httpSession.removeAttribute("assertionChallenge");

        // 署名の検証
        webAuthnService.assertionFinish(
                challenge,
                params.getCredentialId(),
                params.getClientDataJSON(),
                params.getAuthenticatorData(),
                params.getSignature());
    }

}
