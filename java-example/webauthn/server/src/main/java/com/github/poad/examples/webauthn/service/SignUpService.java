package com.github.poad.examples.webauthn.service;

import com.github.poad.examples.webauthn.entity.User;
import com.github.poad.examples.webauthn.exception.UserAlreadyExistException;
import com.github.poad.examples.webauthn.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignUpService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public SignUpService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void signUp(String username, String password) {
        if (repository.existsById(username)) {
            throw new UserAlreadyExistException();
        }
        var user = new User(
                username,
                passwordEncoder.encode(password),
                true,
                true,
                false,
                true,
                null);
        repository.saveAndFlush(user);
    }
}
