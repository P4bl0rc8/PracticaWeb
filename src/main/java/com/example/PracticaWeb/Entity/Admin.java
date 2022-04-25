package com.example.PracticaWeb.Entity;


import com.example.PracticaWeb.Enumerated.Role;
import com.example.PracticaWeb.Security.Filter.View;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

@Entity(name = "Admin")
@Table(name = "Admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.Admin.class)
    private Integer id;

    @Column(nullable = false)
    @JsonView(View.Admin.class)
    private String username;

    @Column(nullable = false)
    @JsonView(View.Admin.class)
    private String password;

    @Enumerated(EnumType.STRING)
    @JsonView(View.Admin.class)
    private Role role = Role.ROLE_ADMIN;

    public Admin(){}

    public Admin(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username;}

    public void setUsername(String username) { this.username = username;}

    public String getPassword() { return password;}

    public void setPassword(String password) { this.password = password;}

    public Role getRole() { return this.role;}
}
