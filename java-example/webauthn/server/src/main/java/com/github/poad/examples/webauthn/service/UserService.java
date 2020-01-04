package com.github.poad.examples.webauthn.service;

import com.github.poad.examples.webauthn.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    protected final UserRepository repository;
    protected final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean existsByUsername(String username) {
        return repository.existsById(username);
    }

    public Optional<User> find(String username) {
        return repository.findById(username).map(entity -> new User(entity.getUsername()));
    }
}
