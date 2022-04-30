package com.example.PracticaWeb.Controller.Rest;


import com.example.PracticaWeb.Entity.Event;
import com.example.PracticaWeb.Entity.Ticket;
import com.example.PracticaWeb.Entity.User;
//import com.example.PracticaWeb.UserHolder;
import com.example.PracticaWeb.Enumerated.Role;
import com.example.PracticaWeb.Security.Filter.View;
import com.example.PracticaWeb.Service.AdminService;
import com.example.PracticaWeb.Service.EventService;
import com.example.PracticaWeb.Service.TicketService;
import com.example.PracticaWeb.Service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;
    @Autowired
    TicketService ticketService;
    @Autowired
    AdminService adminService;

    /*ANOTACION REST CONTROLLERS:
    EL ADMIN TIENE ACCESO A INFO COMPLETA
    SE RESTRINGEN LOS ATRIBUTOS QUE UN USUARIO PUEDE VER
    */



    //CREATE USER//
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(View.User.class)
    public ResponseEntity<User> newUser(@RequestBody User user){

        if (userService.existsUserByUsername(user.getUsername()).isEmpty()&&userService.existsUserByEmail(user.getEmail()).isEmpty()) {
            userService.addUser(user);
            return new ResponseEntity<>(userService.existsUserById(user.getId()).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    //RETURN ALL USERS//
    @GetMapping("/users")
    public Collection<User> allUsers(){ return userService.returnAllUsers(); }

    //RETURN ONE USER BASED ON USERNAME//
    @GetMapping("/users/{username}")
    public ResponseEntity<User> uniqueUser(@PathVariable String username){
        var sec = SecurityContextHolder.getContext().getAuthentication();
        var name= sec.getName();
        if(adminService.existsAdminByUsername(name).isPresent() && userService.existsUserByUsername(username).isPresent()){
            if (adminService.hasRole(name).equals(Role.ROLE_ADMIN)){
                User aux = userService.existsUserByUsername(username).get();
                return new ResponseEntity<>(aux, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }else if( username.equals(name) && userService.existsUserByUsername(name).isPresent()){
            if (userService.hasRole(username).equals(Role.ROLE_USER)){
                User aux = userService.existsUserByUsername(username).get();
                return new ResponseEntity<>(aux, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
       /* if(userService.existsUserByUsername(username).isPresent()){
          return new ResponseEntity<>(userService.existsUserByUsername(username).get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }*/

    }

    //DELETE ONE USER//
    @DeleteMapping("/users/{username}")
    public ResponseEntity<User> deleteUser(@PathVariable String username){

        if(userService.existsUserByUsername(username).isPresent()){
            User aux = userService.existsUserByUsername(username).get();
            userService.deleteUser(username);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //UPDATE AN USER//
    @PutMapping("/users/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User updatedUser){
        var sec = SecurityContextHolder.getContext().getAuthentication();
        var name= sec.getName();
        if(adminService.existsAdminByUsername(username).isPresent()){
            if (adminService.hasRole(name).equals(Role.ROLE_ADMIN)){
                userService.updateUser(updatedUser);
                return new ResponseEntity<>(userService.existsUserByUsername(updatedUser.getUsername()).get(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }else if( username.equals(name) && userService.existsUserByUsername(name).isPresent() && userService.existsUserByUsername(username).get().getEmail().equals(updatedUser.getEmail()) && (userService.existsUserByUsername(updatedUser.getUsername()).isEmpty()||username.equals(updatedUser.getUsername()))){
            if (userService.hasRole(username).equals(Role.ROLE_USER)){
                userService.updateUser(updatedUser);
                return new ResponseEntity<>(userService.existsUserByUsername(updatedUser.getUsername()).get(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        /*if(userService.existsUserByUsername(username).isPresent()){
            userService.updateUser(updatedUser);
            return new ResponseEntity<>(userService.existsUserByUsername(updatedUser.getUsername()).get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }*/

    }
    //SHOWING USER TICKETS//

}
