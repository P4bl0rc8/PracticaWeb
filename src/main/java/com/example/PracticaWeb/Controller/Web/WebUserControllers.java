package com.example.PracticaWeb.Controller.Web;

import com.example.PracticaWeb.Entity.User;
import com.example.PracticaWeb.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;


@Controller
public class WebUserControllers {

    @Autowired
    UserService userService;

    @PostConstruct
    public void init(){
        User u = new User("pepe","pepe@gmail.com","pass123");
        userService.addUser(u);
    }


    @PostMapping("/users/new")
    public String newUser(Model model, User u){
        Optional<User> aux= userService.existsUserByEmail(u.getEmail());
        if (aux.isEmpty()){
            model.addAttribute("user",userService.addUser(u));
            return "showuser";
        } else {
            return "error";
        }
    }

    @PostMapping("/users/update")
    public String updateUser(Model model, User updatedUser){

        Optional<User> aux=userService.existsUserByEmail(updatedUser.getEmail());
        if(aux.isPresent()){
            model.addAttribute("user",userService.updateUser(updatedUser));
            return "showuser";
        }else{
            return "error";
        }
    }

    @PostMapping("/users/delete")
    public String deleteUser(Model model, User u){
        Optional<User> aux=userService.existsUserByUsername(u.getUsername());
        if(aux.isPresent()){
            model.addAttribute("user",aux.get());
            userService.deleteUser(u.getUsername());
            return "deleteuser";

        }else{
            return "error";
        }

    }

    @PostMapping("/users/view")
    public String searchUser(Model model, User u){
        if (userService.existsUserByUsername(u.getUsername()).isPresent()) {
            model.addAttribute("user",userService.existsUserByUsername(u.getUsername()).get());
            return"checkuser";
        }
        else{return "error";}
    }
}
