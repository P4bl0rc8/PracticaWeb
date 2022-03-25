package com.example.PracticaWeb.Repository;

import com.example.PracticaWeb.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer>{

    public Optional<Admin> findAdminByUsername(String username);
}
