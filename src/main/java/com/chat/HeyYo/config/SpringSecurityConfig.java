package com.chat.HeyYo.config;

import com.chat.HeyYo.exception.AuthenticationEntryPointJwt;
import com.chat.HeyYo.security.JwtTokenVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SpringSecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenVerifier authenticationJwtTokenFilter() {

        return new JwtTokenVerifier();
    }

    @Bean
    public AuthenticationEntryPointJwt authenticationEntryPointJwt() {

        return new AuthenticationEntryPointJwt();
    }
}
