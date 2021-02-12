package com.github.poad.examples.webauthn.repository;

import com.github.poad.examples.webauthn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebAuthnUserRepository extends JpaRepository<User, byte[]> {
    @Query(nativeQuery = true, value = "SELECT * FROM user WHERE email = :email")
    Optional<User> find(@Param("email") String email);
}
