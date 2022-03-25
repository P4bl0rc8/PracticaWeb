package com.example.PracticaWeb.Entity;


import javax.persistence.*;

@Entity(name = "Admin")
@Table(name = "Admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    public Admin(){}

    public Admin(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username;}

    public void setUsername(String username) { this.username = username;}

    public String getPassword() { return password;}

    public void setPassword(String password) { this.password = password;}
}
