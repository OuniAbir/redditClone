package com.programming.redditClone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    disable csrf protection for backend (csrf occurs when there are sessions,
//    and we are using cookies to authenticate those sessions
//    and since we are using json web token we can safely disable this csrf feature

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated();  //any other request that doesn't match this pattern should be authenticated

    }

    @Bean
    PasswordEncoder passwordEncoder() {
//         BCryptPasswordEncoder class provided by Spring Security for password hashing before storing in DB
        return new BCryptPasswordEncoder();
    }
}
