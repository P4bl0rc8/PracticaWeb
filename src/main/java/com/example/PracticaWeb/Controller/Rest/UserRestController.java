package com.example.PracticaWeb.Controller.Rest;


import com.example.PracticaWeb.Entity.User;
import com.example.PracticaWeb.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    UserHolder userHolder;

    //CREATE USER//
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User>  newUser(@RequestBody User user){
        User aux = userHolder.unique(user.getUsername());
        if (aux == null) {
            userHolder.addUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    //RETURN ALL USERS//
    @GetMapping("/users")
    public Collection<User> allUsers(){ return userHolder.userCollection(); }

    //RETURN ONE USER BASED ON USERNAME//
    @GetMapping("/users/{username}")
    public ResponseEntity<User> uniqueUsuario(@PathVariable String username){

        if(userHolder.unique(username) != null){
          return new ResponseEntity<>(userHolder.unique(username), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    //DELETE ONE USER//
    @DeleteMapping("/users/{username}")
    public ResponseEntity<User> deleteUser(@PathVariable String username){

        if(userHolder.unique(username) != null){
            return new ResponseEntity<>(userHolder.unique(username), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //UPDATE AN USER//
    @PutMapping("/users/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User updatedUser){

        User aux = userHolder.unique(username);
        if(aux != null){
            userHolder.addUser(username, updatedUser);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
