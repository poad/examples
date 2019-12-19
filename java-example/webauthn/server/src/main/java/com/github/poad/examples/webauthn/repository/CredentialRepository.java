package com.github.poad.examples.webauthn.repository;

import com.github.poad.examples.webauthn.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, byte[]> {
    @Query(nativeQuery = true, value = "SELECT * FROM credential WHERE user_id = :userId")
    List<Credential> finds(@Param("userId") byte[] userId);

    @Query(nativeQuery = true, value = "INSERT INTO credential VALUES (#{#credential.credentialId}, #{#credential.user.id}, #{#credential.public_key}, #{#credential.signature_counter})")
    void insert(Credential credential);
}
