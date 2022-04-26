package com.example.PracticaWeb.Security.Configuration;

import com.example.PracticaWeb.Enumerated.Role;
import com.example.PracticaWeb.Security.AccessControl.RepositoryUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private RepositoryUserDetailsService userDetailsService;

    private Role rol;

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

        //PUBLIC PAGES//
         http
                 .authorizeRequests()
                 .antMatchers("/", "/index", "/login", "/loginError", "/logout", "/register", "/OurSpace", "/events", "/events/{cod}", "/error"
                 ,"/AboutUs", "/Contact", "/assets/**", "/user/new", "/events")
                 .permitAll();

         //USER PAGES//
         http

                 .authorizeRequests()
                 .antMatchers("/events/{cod}/newTicket", "/events/{cod}/checkTicket", "/events/query/"
                         ,"/events/querybygender/", "/users/update", "/users/delete", "/users/view")
                 .hasAnyRole("USER","ADMIN");

         //ADMIN PAGES//
         http
                 .authorizeRequests()
                 .antMatchers( "/GestionEvento", "/GestionUsuario", "/events/new", "/events/update", "/events/delete")
                 .hasRole("ADMIN");
         http.authorizeRequests().antMatchers(HttpMethod.POST,"/events/new").hasAnyRole("ADMIN","USER");

        //LOGIN & LOGOUT//
         http
                 .formLogin()
                 .loginPage("/login")
                 .usernameParameter("username")
                 .passwordParameter("password")
                 .defaultSuccessUrl("/index")
                 .failureUrl("/loginError")
                 .permitAll()
                 .and()
                 .logout()
                 .logoutUrl("/logout")
                 .logoutSuccessUrl("/doLogout")
                 .invalidateHttpSession(true)
                 .deleteCookies("JSESSIONID")
                 .and()
                 .headers()
                 .frameOptions()
                 .disable();

         http
                 .headers()
                 .xssProtection();

        // Disable CSRF at the moment
     }
}