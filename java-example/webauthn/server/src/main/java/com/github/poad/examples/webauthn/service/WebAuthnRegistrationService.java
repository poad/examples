package com.github.poad.examples.webauthn.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.poad.examples.webauthn.config.WebAuthnConfig;
import com.github.poad.examples.webauthn.entity.WebAuthnCredential;
import com.github.poad.examples.webauthn.entity.WebAuthnUser;
import com.github.poad.examples.webauthn.repository.WebAuthnCredentialRepository;
import com.github.poad.examples.webauthn.repository.WebAuthnUserRepository;
import com.webauthn4j.WebAuthnManager;
import com.webauthn4j.converter.util.ObjectConverter;
import com.webauthn4j.data.*;
import com.webauthn4j.data.attestation.statement.COSEAlgorithmIdentifier;
import com.webauthn4j.data.client.Origin;
import com.webauthn4j.data.client.challenge.Challenge;
import com.webauthn4j.data.client.challenge.DefaultChallenge;
import com.webauthn4j.data.extension.client.AuthenticationExtensionsClientInputs;
import com.webauthn4j.data.extension.client.RegistrationExtensionClientInput;
import com.webauthn4j.data.extension.client.SupportedExtensionsExtensionClientInput;
import com.webauthn4j.server.ServerProperty;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WebAuthnRegistrationService {

    private final WebAuthnUserRepository userRepository;
    private final WebAuthnCredentialRepository credentialRepository;
    private final WebAuthnConfig config;

    public WebAuthnRegistrationService(WebAuthnUserRepository userRepository, WebAuthnCredentialRepository credentialRepository, WebAuthnConfig config) {
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
        this.config = config;
    }

    public PublicKeyCredentialCreationOptions creationOptions(WebAuthnUser user) {
        var rpId = config.getRp().getId();
        var rpName = config.getRp().getName();

        var rp = new PublicKeyCredentialRpEntity(rpId, rpName);
        var userId = user.getId();
        var userName = user.getEmail();
        var userDisplayName = user.getDisplayName();

        var userInfo = new PublicKeyCredentialUserEntity(userId, userName, userDisplayName);

        var challenge = new DefaultChallenge();

        var es256 = new PublicKeyCredentialParameters(
                PublicKeyCredentialType.PUBLIC_KEY,
                COSEAlgorithmIdentifier.ES256
        );

        var rs256 = new PublicKeyCredentialParameters(
                PublicKeyCredentialType.PUBLIC_KEY,
                COSEAlgorithmIdentifier.RS256
        );

        var pubKeyCredParams = List.of(es256, rs256);

        var timeout = config.getTimeout();

        var credentials = credentialRepository.finds(userId);
        var excludeCredentials = credentials.stream().map(
                credential -> new PublicKeyCredentialDescriptor(
                        PublicKeyCredentialType.PUBLIC_KEY,
                        credential.getCredentialId(),
                        Set.of()
                )
        ).collect(Collectors.toList());

        var authenticatorAttachment = AuthenticatorAttachment.CROSS_PLATFORM;
        var requireResidentKey = false;
        var userVerification = UserVerificationRequirement.DISCOURAGED;
        var authenticatorSelection = new AuthenticatorSelectionCriteria(
                authenticatorAttachment,
                requireResidentKey,
                userVerification);

        var attestation = AttestationConveyancePreference.NONE;

        // extensions ── 登録の拡張機能
        // ※サンプルコードでは、認証器がWebAuthnのどの拡張機能に対応しているのかを調べる拡張機能 "exts" のコードを記載
        //   https://www.w3.org/TR/webauthn-1/#sctn-supported-extensions-extension
        var extensions = new AuthenticationExtensionsClientInputs<RegistrationExtensionClientInput>(
                Map.of(SupportedExtensionsExtensionClientInput.ID, new SupportedExtensionsExtensionClientInput(true)));

        return new PublicKeyCredentialCreationOptions(
                rp,
                userInfo,
                challenge,
                pubKeyCredParams,
                timeout,
                excludeCredentials,
                authenticatorSelection,
                attestation,
                extensions
        );
    }

    public WebAuthnUser findOrElseCreate(String email, String displayName) {
        return userRepository.find(email)
                .orElseGet(() -> createUser(email, displayName));
    }

    public void creationFinish(
            WebAuthnUser user,
            Challenge challenge,
            byte[] clientDataJSON,
            byte[] attestationObject,
            String clientExtensionsJSON
    ) throws JsonProcessingException {
        var origin = Origin.create(config.getOrigin().asString());

        var rpId = config.getRp().getId();
        var challengeBase64 = new DefaultChallenge(Base64.getEncoder().encode(challenge.getValue()));

        var serverProperty = new ServerProperty(origin, rpId, challengeBase64, null);

        var converter = new ObjectConverter();

        var userVerificationRequired = false;
        var userPresenceRequired = false;
        List<String>  expectedExtensionIds = null;

        Set<String> transports = null;
        var request = new RegistrationRequest(
                attestationObject,
                clientDataJSON,
                clientExtensionsJSON,
                transports
        );

        var params = new RegistrationParameters(
                serverProperty,
                userVerificationRequired,
                userPresenceRequired,
                expectedExtensionIds
        );
        var manager = WebAuthnManager.createNonStrictWebAuthnManager(converter);
        var registrationData = manager.validate(request, params);

        var authData = registrationData.getAttestationObject().getAuthenticatorData();

        var credentialId = authData.getAttestedCredentialData().getCredentialId();

        var authenticator = new AuthenticatorImpl(
                authData.getAttestedCredentialData(),
                registrationData.getAttestationObject().getAttestationStatement(),
                authData.getSignCount(),
                Set.of(),
                registrationData.getClientExtensions(),
                Map.of());

        var signatureCounter = authData.getSignCount();

        if (userRepository.find(user.getEmail()).isEmpty()) {
            userRepository.save(user);
        }

        var authenticatorBin = converter.getCborConverter().writeValueAsBytes(authenticator);

        var credential = new WebAuthnCredential(credentialId, user, authenticatorBin, signatureCounter);

        credentialRepository.save(credential);
    }

    private WebAuthnUser createUser(String email, String displayName) {
        var userId = new byte[32];
        new SecureRandom().nextBytes(userId);

        return new WebAuthnUser(userId, email, displayName, Collections.emptyList());
    }


}
