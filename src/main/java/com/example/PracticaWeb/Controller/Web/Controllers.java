package com.example.PracticaWeb.Controller.Web;

import com.example.PracticaWeb.Entity.Admin;
import com.example.PracticaWeb.Entity.Event;
import com.example.PracticaWeb.Entity.Ticket;
import com.example.PracticaWeb.Entity.User;
import com.example.PracticaWeb.Enumerated.Role;
import com.example.PracticaWeb.Service.AdminService;
import com.example.PracticaWeb.Service.EventService;
import com.example.PracticaWeb.Service.TicketService;
import com.example.PracticaWeb.Service.UserService;
import com.sun.net.httpserver.HttpsServer;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class Controllers {

    //CONTROLLERS TO MAP THE PAGE AND LOAD ALL THE TEMPLATE ATTRIBUTES//

    @Autowired
    TicketService ticketService;
    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;
    @Autowired
    AdminService adminService;

    @RequestMapping("/")
    public String index1(HttpServletRequest request, Model model){
        var aux = SecurityContextHolder.getContext().getAuthentication();
        var username = aux.getName();
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());
        return "index";
    }
    @RequestMapping("/index")
    public String index(HttpServletRequest request, Model model){
        var aux = SecurityContextHolder.getContext().getAuthentication();
        var username = aux.getName();
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());
        return "index";
    }


    @RequestMapping ("/home")
    public String home(HttpServletRequest request, Model model){
        var sec = SecurityContextHolder.getContext().getAuthentication();;
        var username = sec.getName();
        Optional<User> user = userService.existsUserByUsername(username);
        Optional<Admin> admin = adminService.existsAdminByUsername(username);
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());
        if(user.isPresent()){
            if(userService.hasRole(sec.getName()).equals(Role.ROLE_USER)){
                model.addAttribute("user", user.get());
                model.addAttribute("eventList",user.get().getEventsList());
                model.addAttribute("ticketList",user.get().getTicketsList());
                return "userDashboard";
            }
            return "error";
        }else if(admin.isPresent()){
            if(adminService.hasRole(sec.getName()).equals(Role.ROLE_ADMIN)){
                return "adminDashboard";
            }

        }
        return "error";
    }
    @GetMapping("/Contact")
    public String contact(HttpServletRequest request, Model model){
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());
        return "Contact";

    }

    @GetMapping("/AboutUs")
    public String about(HttpServletRequest request, Model model){
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());
        return "AboutUs";
    }
    @GetMapping("/OurSpace")
    public String space(HttpServletRequest request, Model model){
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());
        return "OurSpace";
    }
    @RequestMapping("/GestionEvento")
    public String events(HttpServletRequest request, Model model){
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());
        return "GestionEvento";
    }
    @RequestMapping("/GestionUser")
    public String user(HttpServletRequest request, Model model){
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("token", token.getToken());
        return "GestionUsuario";
    }

}
