package com.example.PracticaWeb.Security.AccessControl;

import com.example.PracticaWeb.Entity.Admin;
import com.example.PracticaWeb.Entity.User;
import com.example.PracticaWeb.Enumerated.Role;
import com.example.PracticaWeb.Repository.AdminRepository;
import com.example.PracticaWeb.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RepositoryUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findUserByUsername(username).orElse(null);
        Admin admin = adminRepository.findAdminByUsername(username).orElse(null);

        if(user != null){
            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
        }else if(admin != null){
            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new org.springframework.security.core.userdetails.User(admin.getUsername(), admin.getPassword(), roles);
        }
        return null;

    }


}
