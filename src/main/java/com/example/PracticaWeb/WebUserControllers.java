package com.example.PracticaWeb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class WebUserControllers {

    @Autowired
    UserHolder userHolder;

    //USER CONTROLLERS//
    @PostMapping("/users/new")
    public String newUser(Model model, Usuario u){
        userHolder.addUser(u);
        model.addAttribute("user", userHolder.unique(u.getUsername()));
        return "showuser";
    }

    @PostMapping("/users/update")
    public String updateUser(Model model, Usuario updatedUser){

        Usuario aux = userHolder.unique(updatedUser.getUsername());
        if(aux != null){
            model.addAttribute("user",updatedUser);
            userHolder.addUser(updatedUser);
            return "showuser";
        }else{
            return "error";
        }
    }

    @PostMapping("/users/delete")
    public String deleteUser(Model model,Usuario u){

        Usuario aux = userHolder.unique(u.getUsername());
        if(aux!=null){
            model.addAttribute("user",userHolder.unique(u.getUsername()));
            userHolder.delete(u.getUsername());
            return "showuser";

        }else{
            return "error";
        }

    }

    @PostMapping("/users/view")
    public String searchUser(Model model, Usuario u){
        if (userHolder.unique(u.getUsername())!=null) {
            model.addAttribute("user",userHolder.unique(u.getUsername()));
            return "showuser";
        }
        else{return "error";}
    }
}
