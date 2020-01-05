package com.github.poad.examples.webauthn.service;

import com.github.poad.examples.webauthn.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignInService {
    private final UserRepository repository;

    public SignInService(
            UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void signIn(String username) {
        var user = repository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + username));
    }
}
