package com.example.PracticaWeb.Controller.Rest;


import com.example.PracticaWeb.Entity.Event;
import com.example.PracticaWeb.Entity.Ticket;
import com.example.PracticaWeb.Entity.User;
//import com.example.PracticaWeb.UserHolder;
import com.example.PracticaWeb.Service.EventService;
import com.example.PracticaWeb.Service.TicketService;
import com.example.PracticaWeb.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    //CREATE USER//
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> newUser(@RequestBody User user){

        if (userService.existsUserById(user.getId()).isEmpty()) {
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

        if(userService.existsUserByUsername(username).isPresent()){
          return new ResponseEntity<>(userService.existsUserByUsername(username).get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    //DELETE ONE USER//
    @DeleteMapping("/users/{username}")
    public ResponseEntity<User> deleteUser(@PathVariable String username){

        if(userService.existsUserByUsername(username).isPresent()){
            Optional<User> aux = userService.existsUserByUsername(username);
            userService.deleteUser(aux.get().getUsername());
            return new ResponseEntity<>(aux.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //UPDATE AN USER//
    @PutMapping("/users/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User updatedUser){

        if(userService.existsUserByUsername(username).isPresent()){
            userService.updateUser(updatedUser);
            return new ResponseEntity<>(userService.existsUserByUsername(updatedUser.getUsername()).get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    //SHOWING USER TICKETS//
    @GetMapping("/users/{username}/tickets")
    public Collection<Ticket> showUserTickets(@PathVariable String username){
        return userService.existsUserByUsername(username).get().getTicketsList();
    }

}
