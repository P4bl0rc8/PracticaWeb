package com.example.PracticaWeb.Security.Configuration;

import com.example.PracticaWeb.Repository.UserRepository;
import com.example.PracticaWeb.Security.AccessControl.RepositoryUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Configuration
@Order(1)
public class RestSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public RepositoryUserDetailsService userDetailsService;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(15, new SecureRandom());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.antMatcher("/api/**");

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/users/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/users/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/users/{username}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/users/{username}").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/users/new").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/events").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/events/{cod}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/events/{cod}/ticket/{id}").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/api/events/{cod}/allTickets").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/events/querbygender").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "api/events/{cod}/ticket").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/api/events/{cod}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/events/{cod}").hasRole("ADMIN");

        http.csrf().disable();
        http.httpBasic();
        http.formLogin().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


}
