package fr.harxen.auth_service.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // REST API: no login form, no http basic
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults());
                //.httpBasic(b -> b.disable());
        // disable behavior "login page"
        http
                .formLogin(form -> form.disable())
                .logout(logout -> logout.disable());

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/health").permitAll()
                        .anyRequest().authenticated()
                        //.anyRequest().permitAll()
                );

        return http.build();
    }
}
