package com.github.poad.examples.webauthn.service;

import com.github.poad.examples.webauthn.repository.UserRepository;
import com.github.poad.examples.webauthn.repository.WebAuthnCredentialRepository;
import com.github.poad.examples.webauthn.repository.WebAuthnUserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UnregisterService {
    private final UserRepository repository;
    private final UserDetailsManager detailsService;
    private final WebAuthnUserRepository userRepository;
    private final WebAuthnCredentialRepository credentialRepository;

    public UnregisterService(UserRepository repository, UserDetailsManager userDetailsManager, WebAuthnUserRepository userRepository, WebAuthnCredentialRepository credentialRepository) {
        this.repository = repository;
        this.detailsService = userDetailsManager;
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
    }

    @Transactional
    public void unregister(String username) {
        repository.findById(username).ifPresent(user -> {
            repository.deleteById(user.getUsername());
            if (user.getWebAuthnUser() != null) {
                var webAuthnUser = user.getWebAuthnUser();
                var credentials = credentialRepository.finds(webAuthnUser.getId());
                credentialRepository.deleteAll(credentials);
                userRepository.delete(webAuthnUser);
            }
            detailsService.deleteUser(user.getUsername());
        });
    }
}
