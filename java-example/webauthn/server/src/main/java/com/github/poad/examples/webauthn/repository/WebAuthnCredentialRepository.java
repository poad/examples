package com.github.poad.examples.webauthn.repository;

import com.github.poad.examples.webauthn.entity.WebAuthnCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WebAuthnCredentialRepository extends JpaRepository<WebAuthnCredential, byte[]> {
    @Query(nativeQuery = true, value = "SELECT * FROM web_authn_credential WHERE user_id = :userId")
    List<WebAuthnCredential> finds(@Param("userId") byte[] userId);

    @Query(nativeQuery = true, value = "SELECT * FROM web_authn_credential WHERE credential_id = :credentialId")
    Optional<WebAuthnCredential> find(@Param("credentialId") byte[] credentialId);

}
