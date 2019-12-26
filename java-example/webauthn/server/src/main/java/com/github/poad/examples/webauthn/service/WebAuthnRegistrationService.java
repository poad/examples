package com.github.poad.examples.webauthn.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.poad.examples.webauthn.entity.Credential;
import com.github.poad.examples.webauthn.entity.User;
import com.github.poad.examples.webauthn.repository.CredentialRepository;
import com.github.poad.examples.webauthn.repository.UserRepository;
import com.webauthn4j.data.*;
import com.webauthn4j.data.attestation.statement.COSEAlgorithmIdentifier;
import com.webauthn4j.data.client.Origin;
import com.webauthn4j.data.client.challenge.Challenge;
import com.webauthn4j.data.client.challenge.DefaultChallenge;
import com.webauthn4j.server.ServerProperty;
import com.webauthn4j.validator.WebAuthnRegistrationContextValidator;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Set;
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
        var userDisplayName = "";

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

        var timeout = 1200000L;

        var credentials = credentialRepository.finds(userId);
        var excludeCredentials = credentials.stream().map(
                credential -> new PublicKeyCredentialDescriptor(
                        PublicKeyCredentialType.PUBLIC_KEY,
                        credential.getCredentialId(),
                        Set.of()
                )
        ).collect(Collectors.toList());


        // authenticatorSelection ── 認証器の要求事項
        var authenticatorAttachment = AuthenticatorAttachment.PLATFORM;
        var userVerification = UserVerificationRequirement.REQUIRED;
        var authenticatorSelection = new AuthenticatorSelectionCriteria(
                authenticatorAttachment,
                false,
                userVerification);

        // attestation ── 認証器のアテステーションを要求
        //   ※サンプルコードでは、アテステーションを要求
        var attestation = AttestationConveyancePreference.NONE;

        // 公開鍵クレデンシャル生成API（navigator.credentials.create）のパラメータを作成
        return new PublicKeyCredentialCreationOptions(
                rp,
                userInfo,
                challenge,
                pubKeyCredParams,
                timeout,
                excludeCredentials,
                authenticatorSelection,
                attestation,
                null
        );
    }

    public User findOrElseCreate(String email) {
        return userRepository.find(email)
                .orElseGet(() -> createUser(email));
    }

    public void creationFinish(
            User user,
            Challenge challenge,
            byte[] clientDataJSON,
            byte[] attestationObject
    ) throws JsonProcessingException {
        var origin = Origin.create("http://localhost:8080");

        var rpId = "localhost";
        var challengeBase64 = new DefaultChallenge(Base64.getEncoder().encode(challenge.getValue()));

        var serverProperty = new ServerProperty(origin, rpId, challengeBase64, null);

        var registrationContext = new WebAuthnRegistrationContext(
                clientDataJSON,
                attestationObject,
                serverProperty,
                true
        );

        var validator = WebAuthnRegistrationContextValidator.createNonStrictRegistrationContextValidator();

        var response = validator.validate(registrationContext);

        var authData = response.getAttestationObject().getAuthenticatorData();

        var credentialId = authData
                .getAttestedCredentialData()
                .getCredentialId();

        var publicKey = authData
                .getAttestedCredentialData()
                .getCOSEKey();

        var signatureCounter = authData
                .getSignCount();

        if (userRepository.find(user.getEmail()).isEmpty()) {
            userRepository.insert(user);
        }

        var publicKeyBin = new ObjectMapper()
                .writeValueAsBytes(publicKey);

        var credential = new Credential(credentialId, user, publicKeyBin, signatureCounter);

        credentialRepository.insert(credential);
    }

    private User createUser(String email) {
        var userId = new byte[32];
        new SecureRandom().nextBytes(userId);

        return new User(userId, email, displayName);
    }


}
