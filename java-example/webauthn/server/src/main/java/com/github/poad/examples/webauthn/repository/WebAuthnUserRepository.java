package com.github.poad.examples.webauthn.repository;

import com.github.poad.examples.webauthn.entity.WebAuthnUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebAuthnUserRepository extends JpaRepository<WebAuthnUser, byte[]> {
    @Query(nativeQuery = true, value = "SELECT * FROM webauthn_user WHERE email = :email")
    Optional<WebAuthnUser> find(@Param("email") String email);
}
