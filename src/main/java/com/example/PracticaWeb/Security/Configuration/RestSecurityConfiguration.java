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
<<<<<<< HEAD
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
=======
                .antMatchers(HttpMethod.GET, "/api/users/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/users/{username}").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/users/{username}").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/users/{username}").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/users/new").permitAll()
                        .antMatchers("/assets/**").permitAll();
>>>>>>> parent of dfb1876 (API REST securizada)

        http.csrf().disable();
        http.httpBasic();

    }


}
