package com.github.poad.examples.webauthn.security;

import com.github.poad.examples.webauthn.entrypoint.RestAuthenticationEntryPoint;
import com.github.poad.examples.webauthn.handler.RestSavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final RestSavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler;
    private final SimpleUrlAuthenticationFailureHandler authenticationFailureHandler;
    private final SimpleUrlLogoutSuccessHandler logoutSuccessHandler;

    public SecurityConfig(
            RestAuthenticationEntryPoint authenticationEntryPoint,
            RestSavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler,
            SimpleUrlAuthenticationFailureHandler authenticationFailureHandler,
            SimpleUrlLogoutSuccessHandler logoutSuccessHandler) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;

        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @Override
    public  void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/attestation/**",
                "/assertion/**",
                "/user/*",
                "/signup",
                "/login",
                "/console/*",
                "/actuator",
                "/actuator/*",
                "/error");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .httpBasic()
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        return new InMemoryUserDetailsManager();
    }

    @Bean
    public AuthenticationProvider autheticationProvider(UserDetailsManager userDetailsManager) {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsManager);
        return provider;
    }
}
