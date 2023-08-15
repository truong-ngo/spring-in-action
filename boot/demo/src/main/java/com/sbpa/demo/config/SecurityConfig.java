package com.sbpa.demo.config;

import com.sbpa.demo.repository.ReaderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final ReaderRepo repo;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").hasRole("READER")
                        .requestMatchers("/**").permitAll())
                .formLogin(login -> login
                        .loginPage("/login")
                        .failureUrl("/login?error=true"))
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repo.findById(username).orElse(null);
    }
}
