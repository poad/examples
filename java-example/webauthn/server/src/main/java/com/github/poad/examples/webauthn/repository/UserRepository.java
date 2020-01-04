package com.github.poad.examples.webauthn.repository;

import com.github.poad.examples.webauthn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query(nativeQuery = true, value = "SELECT (CASE WHEN COUNT(username) > 0 THEN true ELSE false END) FROM users WHERE username = :username AND encoded_password = :password")
    boolean existsByIdAndPassword(@Param("username") String username, @Param("password") String password);
}
