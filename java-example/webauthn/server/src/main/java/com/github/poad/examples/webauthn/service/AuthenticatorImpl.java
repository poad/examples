package com.github.poad.examples.webauthn.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.webauthn4j.authenticator.Authenticator;
import com.webauthn4j.data.AuthenticatorTransport;
import com.webauthn4j.data.attestation.authenticator.AttestedCredentialData;
import com.webauthn4j.data.attestation.statement.AttestationStatement;
import com.webauthn4j.data.extension.authenticator.RegistrationExtensionAuthenticatorOutput;
import com.webauthn4j.data.extension.client.RegistrationExtensionClientOutput;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

// Authenticatorのシリアライズ／デシリアライズの方法
//   https://github.com/webauthn4j/webauthn4j/issues/148
public class AuthenticatorImpl implements Authenticator {

    private AttestedCredentialData attestedCredentialData;
    private AttestationStatement attestationStatement;
    private Set<AuthenticatorTransport> transports;
    private long counter;

    private Map<String, RegistrationExtensionClientOutput> clientExtensions;
    private Map<String, RegistrationExtensionAuthenticatorOutput> authenticatorExtensions;

    public AuthenticatorImpl(
            @JsonProperty("attestedCredentialData") AttestedCredentialData attestedCredentialData,
            @JsonProperty("attestationStatement") AttestationStatement attestationStatement,
            @JsonProperty("counter") long counter,
            @JsonProperty("transports") Set<AuthenticatorTransport> transports,
            @JsonProperty("clientExtensions") Map<String, RegistrationExtensionClientOutput> clientExtensions,
            @JsonProperty("authenticatorExtensions") Map<String, RegistrationExtensionAuthenticatorOutput> authenticatorExtensions) {
        this.attestedCredentialData = attestedCredentialData;
        this.attestationStatement = attestationStatement;
        this.transports = Collections.unmodifiableSet(transports);
        this.clientExtensions = clientExtensions;
        this.authenticatorExtensions = authenticatorExtensions;
        setCounter(counter);
    }

    @Override
    public AttestedCredentialData getAttestedCredentialData() {
        return attestedCredentialData;
    }

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "format"
    )
    @Override
    public AttestationStatement getAttestationStatement() {
        return attestationStatement;
    }

    @JsonProperty("format")
    public String getFormat() {
        return attestationStatement.getFormat();
    }

    @Override
    public Set<AuthenticatorTransport> getTransports() {
        return transports;
    }

    @Override
    public long getCounter() {
        return counter;
    }

    @Override
    public void setCounter(long counter) {
        this.counter = counter;
    }

    @Override
    public Map<String, RegistrationExtensionClientOutput> getClientExtensions() {
        return clientExtensions;
    }

    @Override
    public Map<String, RegistrationExtensionAuthenticatorOutput> getAuthenticatorExtensions() {
        return authenticatorExtensions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticatorImpl that = (AuthenticatorImpl) o;
        return counter == that.counter &&
                Objects.equals(attestedCredentialData, that.attestedCredentialData) &&
                Objects.equals(attestationStatement, that.attestationStatement) &&
                Objects.equals(transports, that.transports) &&
                Objects.equals(clientExtensions, that.clientExtensions) &&
                Objects.equals(authenticatorExtensions, that.authenticatorExtensions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attestedCredentialData, attestationStatement, transports, counter, clientExtensions, authenticatorExtensions);
    }

}
