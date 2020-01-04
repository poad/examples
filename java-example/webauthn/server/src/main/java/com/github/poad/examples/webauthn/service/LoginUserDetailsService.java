package com.github.poad.examples.webauthn.service;

import com.github.poad.examples.webauthn.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

//@Service
public class LoginUserDetailsService implements UserDetailsManager {
    protected final UserRepository repository;
    protected final PasswordEncoder passwordEncoder;

    public LoginUserDetailsService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(UserDetails user) {
        repository.saveAndFlush(((LoginUserDetail)user).getUser());
    }

    @Override
    public void updateUser(UserDetails user) {
        repository.saveAndFlush(((LoginUserDetail)user).getUser());
    }

    @Override
    public void deleteUser(String username) {
        repository.deleteById(username);

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
    }

    @Override
    public boolean userExists(String username) {
        return repository.existsById(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findById(username).orElseThrow(() -> new UsernameNotFoundException("Not Found " + username));
        return new LoginUserDetail(user);
    }
}
