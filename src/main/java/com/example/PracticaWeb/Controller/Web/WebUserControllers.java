package com.example.PracticaWeb.Controller.Web;

import com.example.PracticaWeb.Entity.User;
import com.example.PracticaWeb.Enumerated.Role;
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

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Model model){
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());
        return "login";
    }

    @GetMapping("/loginError")
    public String loginerror(HttpServletRequest request, Model model){
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());
        return "loginError";
    }

    @GetMapping("/register")
    public String register(HttpServletRequest request, Model model){
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());
        return "register";
    }

    @PostMapping("/users/new")
    public String newUser(HttpServletRequest request,Model model, User u){
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());
        var sec = SecurityContextHolder.getContext().getAuthentication();
        User aux = userService.existsUserByUsername(u.getUsername()).get();
        if(aux == null){
            if(sec.getName().equals("anonymousUser")){
                userService.addUser(u);
                model.addAttribute("user", u);
                return "userDashboard";
            }else if(userService.hasRole(sec.getName()).equals(Role.ROLE_ADMIN)){
                return "GestionUsuario";
            }else{
                return "error";
            }
        }else{
            return "error";
        }

    }

    @PostMapping("/users/update")
    public String updateUser(HttpServletRequest request, Model model, User u){
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());
        var sec = SecurityContextHolder.getContext().getAuthentication();
        User aux = userService.existsUserByUsername(u.getUsername()).get();
        if(aux != null && userService.equalsUser(aux, sec.getName())){
            if(userService.hasRole(sec.getName()).equals(Role.ROLE_USER) && sec.getName().equals(aux.getUsername() )){
                userService.updateUser(u);
                model.addAttribute("user", u);
                return "userDashboard";
            }else if(userService.hasRole(sec.getName()).equals(Role.ROLE_ADMIN)){
                return "showUsersAdmin";
            }else{
                return "error";
            }
        }else{
            return "error";
        }

    }

    @PostMapping("/users/delete")
    public String deleteUser(HttpServletRequest request, Model model, User u){
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());
        var sec = SecurityContextHolder.getContext().getAuthentication();
        User aux = userService.existsUserByUsername(u.getUsername()).get();
        if(aux != null && userService.equalsUser(aux, sec.getName())){
            if(userService.hasRole(sec.getName()).equals(Role.ROLE_USER)){
                userService.deleteUser(u.getUsername());
                model.addAttribute("user", u);
                return "login";
            }else if(userService.hasRole(sec.getName()).equals(Role.ROLE_ADMIN)){
                return "showUsersAdmin";
            }else{
                return "error";
            }
        }else{
            return "error";
        }
    }

    @PostMapping("/users/view")
    public String searchUser(Model model, User u, HttpServletRequest request){
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());
        var sec = SecurityContextHolder.getContext().getAuthentication();
        User aux = userService.existsUserByUsername(u.getUsername()).get();
        if(aux == null){
            if(sec.getName().equals("anonymousUser")){
                userService.addUser(u);
                model.addAttribute("user", u);
                return "userDashboard";
            }else if(userService.hasRole(sec.getName()).equals(Role.ROLE_ADMIN)){
                model.addAttribute(u);
                return "showUsersAdmin";
            }else{
                return "error";
            }
        }else{
            return "error";
        }
    }
}
