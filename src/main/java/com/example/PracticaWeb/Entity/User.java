package com.example.PracticaWeb.Entity;

import com.example.PracticaWeb.Enumerated.Role;
import com.example.PracticaWeb.Security.Filter.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @ManyToMany
    private List<Event> eventsList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Ticket> ticketsList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private final Role role = Role.ROLE_USER;

    public User(String username, String email, String pass, Role role) {
        this.username=username;
        this.email=email;
        this.password=pass;
 
    }
    public User(String username, String email, String pass){
        this.username = username;
        this.email = email;
        this.password = pass;
    }
    public User(User u){
        this.username=u.username;
        this.email=u.email;
        this.id=u.id;
        this.password="";
        this.eventsList= new ArrayList<>();
        this.ticketsList=new ArrayList<>();
    }

    //GETTERS//
    public String getPassword(){
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public long getId(){
        return this.id;
    }

    public List<Event> getEventsList() {
        return eventsList;
    }

    public List<Ticket> getTicketsList() {
        return ticketsList;
    }

    public Role getRole(){ return this.role;}


    //SETTERS//
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //EVENT FUNCTIONALITY//
    public boolean attendEvent(Event event){
        return this.eventsList.contains(event);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(role.toString()));
        return roles;
    }
}
