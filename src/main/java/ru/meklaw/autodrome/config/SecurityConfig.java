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
import ru.meklaw.autodrome.service.PersonDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .ignoringRequestMatchers("/api/**");

        http
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/api/auth/**")
                                .permitAll()
                                .requestMatchers("/api/**")
                                .hasRole("MANAGER")
                )
                .httpBasic();

        http
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/auth/**")
                                .permitAll()
                                .requestMatchers("/**")
                                .hasRole("MANAGER")
                )
                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/enterprises", true)
                .failureUrl("/auth/login?error")
                .and()
                .logout()//?????????????????? ????????????
                .logoutUrl("/logout")//?????????????? ???? ?????????? ???????????????? ?????????? ?????????????????????? ??????????
                .logoutSuccessUrl("/auth/login"); //?????????? ???????????????? ?????????? ?????????????????????? ?????????? ????????????;


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(personDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
