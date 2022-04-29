package com.example.PracticaWeb.Controller.Web;

import com.example.PracticaWeb.Entity.User;
import com.example.PracticaWeb.Enumerated.Role;
import com.example.PracticaWeb.Service.AdminService;
import com.example.PracticaWeb.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.web.csrf.CsrfToken;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Controller
public class WebUserControllers {

    //CONTROLLERS TO MAP ALL THE USER WEB FUNCTIONALITY//

    @Autowired
    UserService userService;
    @Autowired
    AdminService adminService;

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Model model){
        return "login";
    }
    @GetMapping("/loginError")
    public String loginerror(HttpServletRequest request, Model model){
        return "loginError";
    }

    @GetMapping("/register")
    public String register(HttpServletRequest request, Model model){
        return "register";
    }

    @PostMapping("/users/new")
    public String newUser(HttpServletRequest request,Model model, User u){
        var sec = SecurityContextHolder.getContext().getAuthentication();

        if(userService.existsUserByUsername(u.getUsername()).isEmpty()){
            if(sec.getName().equals("anonymousUser")){
                userService.addUser(u);
                model.addAttribute("user", u);
                return "login";
            }else if(adminService.hasRole(sec.getName()).equals(Role.ROLE_ADMIN)){
                userService.addUser(u);
                return "adminDashboard";
            }else{
                return "error";
            }
        }else{
            return "error";
        }

    }

    @PostMapping("/users/update")
    public String updateUser(HttpServletRequest request, Model model, User u){
        var sec = SecurityContextHolder.getContext().getAuthentication();
        var username= sec.getName();
        if(adminService.existsAdminByUsername(username).isPresent()){
            if (adminService.hasRole(username).equals(Role.ROLE_ADMIN)){
                userService.updateUser(u);
                return"adminDashboard";
            }else{
                return "error";
            }
        }else if(userService.existsUserByUsername(username).isPresent() && userService.existsUserByUsername(username).get().getEmail().equals(u.getEmail()) && (userService.existsUserByUsername(u.getUsername()).isEmpty()||username.equals(u.getUsername()))){
            if (userService.hasRole(username).equals(Role.ROLE_USER)){
                model.addAttribute("user", userService.updateUser(u));
                return "login";
            }else{
                return "error";
            }
        }else{
            return "error";
        }
    }

    @PostMapping("/users/delete")
    public String deleteUser(HttpServletRequest request, Model model, User u){
        var sec = SecurityContextHolder.getContext().getAuthentication();
        var username= sec.getName();
        if(userService.existsUserByUsername(u.getUsername()).isPresent()){
            if(adminService.hasRole(username).equals(Role.ROLE_ADMIN)){
                userService.deleteUser(u.getUsername());
                return "adminDashboard";
            }else{
                return "error";
            }
        }else{
            return "error";
        }
    }

}
