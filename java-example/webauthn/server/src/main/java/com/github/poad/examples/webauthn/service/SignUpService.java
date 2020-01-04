package com.github.poad.examples.webauthn.service;

import com.github.poad.examples.webauthn.entity.User;
import com.github.poad.examples.webauthn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignUpService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsManager detailsService;


    public SignUpService(UserRepository repository, PasswordEncoder passwordEncoder, @Qualifier("userDetailsManager") UserDetailsManager detailsService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.detailsService = detailsService;
    }

    @Transactional
    public void signUp(String username, String password) {
        var user = new User(
                username,
                passwordEncoder.encode(password),
                true,
                true,
                false,
                true,
                null);
        repository.saveAndFlush(user);
        detailsService.createUser(new LoginUserDetail(user));
    }
}
