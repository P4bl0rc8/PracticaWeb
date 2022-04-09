package com.example.PracticaWeb.Controller.Web;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class BasicConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http ) throws Exception{
        http.csrf().disable().authorizeRequests().anyRequest().permitAll()
                .and()
                .formLogin()
                .and()
                .httpBasic();
        http.headers().frameOptions().disable();
    }

}
