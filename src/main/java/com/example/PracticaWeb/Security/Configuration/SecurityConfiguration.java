package com.example.PracticaWeb.Security.Configuration;

import com.example.PracticaWeb.Enumerated.Role;
import com.example.PracticaWeb.Security.AccessControl.CustomOAuth2User;
import com.example.PracticaWeb.Security.AccessControl.CustomOAuth2UserService;
import com.example.PracticaWeb.Security.AccessControl.RepositoryUserDetailsService;
import com.example.PracticaWeb.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private RepositoryUserDetailsService userDetailsService;
    @Autowired
    private CustomOAuth2UserService oauthUserService;
    @Autowired
    private UserService userService;
    private Role rol;

     @Override
     protected void configure(HttpSecurity http) throws Exception {

        //PUBLIC PAGES//
         http
                 .authorizeRequests()
                 .antMatchers("/", "/index", "/login", "/loginError", "/logout", "/register", "/OurSpace", "/events", "/events/{cod}", "/error"
                 ,"/AboutUs", "/Contact", "/assets/**", "/user/new", "/events","/oauth/**")
                 .permitAll();

         //USER PAGES//
         http

                 .authorizeRequests()
                 .antMatchers("/events/{cod}/newTicket", "/events/{cod}/checkTicket", "/events/query/"
                         ,"/events/querybygender/", "/users/update", "/users/delete", "/users/view","/home")
                 .hasAnyRole("USER","ADMIN");

         //ADMIN PAGES//
         http
                 .authorizeRequests()
                 .antMatchers( "/GestionEvento", "/GestionUsuario", "/events/new", "/events/update", "/events/delete")
                 .hasRole("ADMIN");

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
                 .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                 .logoutSuccessUrl("/")
                 .invalidateHttpSession(true)
                 .deleteCookies("JSESSIONID")
                 .and()
                 .headers()
                 .frameOptions()
                 .disable();

         http
                 .oauth2Login()
                 .loginPage("/login")
                 .userInfoEndpoint()
                 .userService(oauthUserService)
                 .and()
                 .successHandler(new AuthenticationSuccessHandler() {
                     @Override
                     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                         DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();

                         userService.processOAuthPostLogin(oauthUser);

                         response.sendRedirect("/");
                     }
                 });

         http
                 .headers()
                 .xssProtection();

        // Disable CSRF at the moment
     }


}
