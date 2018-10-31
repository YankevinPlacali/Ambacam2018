package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;

@Configuration
public class SecurityConfig extends GlobalAuthenticationConfigurerAdapter {

    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("herval")
                .password("{noop}herval")
                .roles("USER")
                .and()
                .withUser("admin")
                .password("{noop}admin")
                .roles("USER", "ADMIN");
    }

}
