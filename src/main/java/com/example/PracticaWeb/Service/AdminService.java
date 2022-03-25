package com.example.PracticaWeb.Service;

import com.example.PracticaWeb.Entity.Admin;
import com.example.PracticaWeb.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    public Admin addAdmin(Admin admin){
        return adminRepository.save(admin);
    }
}
