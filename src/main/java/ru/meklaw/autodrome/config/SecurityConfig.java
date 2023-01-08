package ru.meklaw.autodrome.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.meklaw.autodrome.service.ManageDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final ManageDetailsService manageDetailsService;

    @Autowired
    public SecurityConfig(ManageDetailsService manageDetailsService) {
        this.manageDetailsService = manageDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .ignoringRequestMatchers("/api/**")
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/**").authenticated()
                .and()
                .formLogin();

        http
                .authorizeHttpRequests()
                .requestMatchers("/api/**").hasRole("MANAGER")
                .and()
                .httpBasic();

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(manageDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
