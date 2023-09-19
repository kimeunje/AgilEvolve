package com.taskagile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  public static final String[] PUBLIC = new String[] {
      // 경로
      "/error",
      "/login",
      "/logout",
      "/register",
      "/api/registrations",

      // 정적 자원
      "/static/**",
      "/js/**",
      "/css/**",
      "/images/**",
      "/favicon.ico"
  };

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((requests) -> requests
            .requestMatchers(PUBLIC).permitAll()
            .anyRequest().authenticated())
        .formLogin((form) -> form
            .loginPage("/login"))
        .logout((logout) -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logged-out"))
        .csrf((csrf) -> csrf.disable());

    return http.build();

  }
}
