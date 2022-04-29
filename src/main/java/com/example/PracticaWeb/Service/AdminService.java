package com.example.PracticaWeb.Service;

import com.example.PracticaWeb.Entity.Admin;
import com.example.PracticaWeb.Enumerated.Role;
import com.example.PracticaWeb.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    public Admin addAdmin(Admin admin){
        return adminRepository.save(admin);
    }
    public Optional<Admin> existsAdminByUsername(String username){return adminRepository.findAdminByUsername(username);}
    public Role hasRole(String username){
        try{
            return adminRepository.findAdminByUsername(username).get().getRole();}
        catch (NoSuchElementException exception){
            return null;
        }
    }
}
