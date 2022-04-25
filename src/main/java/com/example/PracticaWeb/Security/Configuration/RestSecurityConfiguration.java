package com.example.PracticaWeb.Security.Configuration;

import com.example.PracticaWeb.Repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(1)
public class RestSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.antMatcher("/api/**");

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/users/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/users/{username}").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/users/{username}").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/users/{username}").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/users/new").permitAll()
                        .antMatchers("/assets/**").permitAll();

        http.csrf().disable();
        http.httpBasic();

    }


}
