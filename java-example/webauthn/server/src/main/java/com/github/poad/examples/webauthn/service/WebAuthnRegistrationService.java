package com.github.poad.examples.webauthn.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.poad.examples.webauthn.entity.Credential;
import com.github.poad.examples.webauthn.entity.User;
import com.github.poad.examples.webauthn.repository.CredentialRepository;
import com.github.poad.examples.webauthn.repository.UserRepository;
import com.webauthn4j.converter.util.CborConverter;
import com.webauthn4j.data.*;
import com.webauthn4j.data.attestation.statement.COSEAlgorithmIdentifier;
import com.webauthn4j.data.client.Origin;
import com.webauthn4j.data.client.challenge.Challenge;
import com.webauthn4j.data.client.challenge.DefaultChallenge;
import com.webauthn4j.data.extension.client.AuthenticationExtensionsClientInputs;
import com.webauthn4j.data.extension.client.RegistrationExtensionClientInput;
import com.webauthn4j.data.extension.client.SupportedExtensionsExtensionClientInput;
import com.webauthn4j.server.ServerProperty;
import com.webauthn4j.validator.WebAuthnRegistrationContextValidator;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WebAuthnRegistrationService {

    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;

    public WebAuthnRegistrationService(UserRepository userRepository, CredentialRepository credentialRepository) {
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
    }

    public PublicKeyCredentialCreationOptions creationOptions(User user) {
        var rpId = "localhost";
        var rpName = "Example Relying Party";

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

        var timeout = 60000L;

        var credentials = credentialRepository.finds(userId);
        var excludeCredentials = credentials.stream().map(
                credential -> new PublicKeyCredentialDescriptor(
                        PublicKeyCredentialType.PUBLIC_KEY,
                        credential.getCredentialId(),
                        Set.of()
                )
        ).collect(Collectors.toList());

        var authenticatorAttachment = AuthenticatorAttachment.CROSS_PLATFORM;
        var userVerification = UserVerificationRequirement.DISCOURAGED;
        var authenticatorSelection = new AuthenticatorSelectionCriteria(
                authenticatorAttachment,
                false,
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

    public User findOrElseCreate(String email, String displayName) {
        return userRepository.find(email)
                .orElseGet(() -> createUser(email, displayName));
    }

    public void creationFinish(
            User user,
            Challenge challenge,
            byte[] clientDataJSON,
            byte[] attestationObject
    ) throws JsonProcessingException {
        var origin = Origin.create("http://localhost:3000");

        var rpId = "localhost";
        var challengeBase64 = new DefaultChallenge(Base64.getEncoder().encode(challenge.getValue()));

        var serverProperty = new ServerProperty(origin, rpId, challengeBase64, null);

        var registrationContext = new WebAuthnRegistrationContext(
                clientDataJSON,
                attestationObject,
                serverProperty,
                false
        );

//        var validator = new WebAuthnRegistrationContextValidator(
//                // アテステーション・ステートメントのフォーマットは全部で6種類
//                List.of(
//                        // https://www.w3.org/TR/webauthn-1/#packed-attestation
//                        new PackedAttestationStatementValidator(),
//                        // https://www.w3.org/TR/webauthn-1/#tpm-attestation
//                        new TPMAttestationStatementValidator(),
//                        // https://www.w3.org/TR/webauthn-1/#android-key-attestation
//                        new AndroidKeyAttestationStatementValidator(),
//                        // https://www.w3.org/TR/webauthn-1/#android-safetynet-attestation
//                        new AndroidSafetyNetAttestationStatementValidator(),
//                        // https://www.w3.org/TR/webauthn-1/#fido-u2f-attestation
//                        new FIDOU2FAttestationStatementValidator(),
//                        // https://www.w3.org/TR/webauthn-1/#none-attestation
//                        new NoneAttestationStatementValidator()
//                ),
//                new NullCertPathTrustworthinessValidator(),
//                new NullECDAATrustworthinessValidator(),
//                new DefaultSelfAttestationTrustworthinessValidator()
//        );
        var validator = WebAuthnRegistrationContextValidator.createNonStrictRegistrationContextValidator();

        var response = validator.validate(registrationContext);

        var authData = response.getAttestationObject().getAuthenticatorData();

        var credentialId = response
                .getAttestationObject()
                .getAuthenticatorData()
                .getAttestedCredentialData()
                .getCredentialId();

        var authenticator = new AuthenticatorImpl(
                response.getAttestationObject().getAuthenticatorData().getAttestedCredentialData(),
                response.getAttestationObject().getAttestationStatement(),
                response.getAttestationObject().getAuthenticatorData().getSignCount(),
                Set.of(),
                response.getRegistrationExtensionsClientOutputs(),
                Map.of());

        var signatureCounter = response
                .getAttestationObject()
                .getAuthenticatorData()
                .getSignCount();

        if (userRepository.find(user.getEmail()).isEmpty()) {
            userRepository.save(user);
        }

        var authenticatorBin = new CborConverter().writeValueAsBytes(authenticator);

        var credential = new Credential(credentialId, user, authenticatorBin, signatureCounter);

        credentialRepository.save(credential);
    }

    private User createUser(String email, String displayName) {
        var userId = new byte[32];
        new SecureRandom().nextBytes(userId);

        return new User(userId, email, displayName, Collections.emptyList());
    }


}
