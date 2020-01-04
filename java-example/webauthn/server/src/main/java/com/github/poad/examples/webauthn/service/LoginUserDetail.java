package com.github.poad.examples.webauthn.service;

import com.github.poad.examples.webauthn.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class LoginUserDetail extends org.springframework.security.core.userdetails.User {
    private final User user;
    public LoginUserDetail(User user) {
        this(
                user.getUsername(),
                user.getEncodedPassword(),
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked(),
                AuthorityUtils.createAuthorityList("ROLE_USER"));
    }

    public LoginUserDetail(String username, String password, boolean enabled,
                           boolean accountNonExpired, boolean credentialsNonExpired,
                           boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = new User(username, password, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled,null);
    }

    public User getUser() {
        return user;
    }
}
