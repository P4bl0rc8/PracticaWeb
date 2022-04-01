package com.example.PracticaWeb.Controller.Web;

import com.example.PracticaWeb.Entity.User;
import com.example.PracticaWeb.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;


@Controller
public class WebUserControllers {

    @Autowired
    UserService userService;

    @PostConstruct
    public void init(){
        User u = new User("pepe","pepe@gmail.com","pass123");
        userService.addUser(u);
    }

/*
    @PostMapping("/users/new")
    public String newUser(Model model, User u){
        User aux = userHolder.unique(u.getUsername());
        if (aux == null) {
            userHolder.addUser(u);
            model.addAttribute("user", userHolder.unique(u.getUsername()));
            return "showuser";
        } else {
            return "error";
        }
    }

    @PostMapping("/users/update")
    public String updateUser(Model model, User updatedUser){

        User aux = userHolder.unique(updatedUser.getUsername());
        if(aux != null){
            model.addAttribute("user",updatedUser);
            userHolder.addUser(updatedUser);
            return "showuser";
        }else{
            return "error";
        }
    }

    @PostMapping("/users/delete")
    public String deleteUser(Model model, User u){

        User aux = userHolder.unique(u.getUsername());
        if(aux!=null){
            model.addAttribute("user",userHolder.unique(u.getUsername()));
            userHolder.delete(u.getUsername());
            return "deleteuser";

        }else{
            return "error";
        }

    }

    @PostMapping("/users/view")
    public String searchUser(Model model, User u){
        if (userHolder.unique(u.getUsername())!=null) {
            model.addAttribute("user",userHolder.unique(u.getUsername()));
            return "checkuser";
        }
        else{return "error";}
    }*/
}