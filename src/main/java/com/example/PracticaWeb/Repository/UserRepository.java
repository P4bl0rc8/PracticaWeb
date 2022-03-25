package com.example.PracticaWeb.Repository;

import com.example.PracticaWeb.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findUserByUsername(String username);
    public Optional<User> findUserById(long id);
    public Optional<User> findUserByEmail(String email);
}
