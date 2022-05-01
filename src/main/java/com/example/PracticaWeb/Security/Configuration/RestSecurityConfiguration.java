package com.example.PracticaWeb.Security.Configuration;

import com.example.PracticaWeb.Entity.User;
import com.example.PracticaWeb.Repository.UserRepository;
import com.example.PracticaWeb.Security.AccessControl.RepositoryUserDetailsService;
import com.example.PracticaWeb.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.Collection;

@Configuration
@Order(1)
public class RestSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Autowired
    RepositoryUserDetailsService UserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.antMatcher("/api/**");

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/events/").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/events/{cod}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/events/{cod}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/events/{cod}/ticket").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/api/events/{cod}/ticket/id").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/api/users/{username}").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.POST, "/api/users/").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/users/").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/users/{username}").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.GET,"/events/{cod}/alltickets").hasRole("ADMIN")
                .antMatchers("/assets/**").permitAll(); 
        http.csrf().disable();
        http.httpBasic();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(UserDetailsService).passwordEncoder(new BCryptPasswordEncoder(15, new SecureRandom()));
    }


}
