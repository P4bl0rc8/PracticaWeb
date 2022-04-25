package com.example.PracticaWeb.Security.AccessControl;

import com.example.PracticaWeb.Entity.Admin;
import com.example.PracticaWeb.Entity.User;
import com.example.PracticaWeb.Enumerated.Role;
import com.example.PracticaWeb.Repository.AdminRepository;
import com.example.PracticaWeb.Repository.UserRepository;
import com.example.PracticaWeb.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DatabaseUserLoader {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostConstruct
    private void initDB() {
        adminRepository.save(new Admin("admin", encoder.encode("vivaerbeti")));
        userRepository.save(new User("pablorc08", "pabloredondo@gmail.com", encoder.encode("dellafuentefurboclu"), Role.ROLE_USER));
        userRepository.save(new User("2k9Pablo", "pablopastor@gmail.com", encoder.encode("pablico99suprimo"), Role.ROLE_USER));

    }
}
