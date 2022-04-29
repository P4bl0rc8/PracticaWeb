package com.example.PracticaWeb.Security.Authentication;

import com.example.PracticaWeb.Entity.User;
import com.example.PracticaWeb.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
@Service
public class UserRepositoryAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException{
        Optional<User> useraux = userRepository.findUserByUsername(authentication.getName());
        var user = useraux.orElse(null);
        String password = (String) authentication.getCredentials();

        if(user == null || new BCryptPasswordEncoder().matches(password, user.getPassword())){
            throw new BadCredentialsException("Wrong Credentials");
        }
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().toString()));
        return new UsernamePasswordAuthenticationToken(user.getUsername(), password, roles);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
