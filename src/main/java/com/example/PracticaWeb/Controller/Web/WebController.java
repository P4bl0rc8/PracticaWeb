package com.example.PracticaWeb.Controller.Web;


import com.example.PracticaWeb.Entity.Event;
import com.example.PracticaWeb.Entity.Ticket;
//import com.example.PracticaWeb.EventHolder;
import com.example.PracticaWeb.Service.EventService;
import com.example.PracticaWeb.Service.TicketService;
import com.example.PracticaWeb.Service.UserService;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

@Controller
public class WebController {

    //CONTROLLERS TO MAP ALL THE EVENTS WEB FUNCTIONALITY//

    @Autowired
    TicketService ticketService;
    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;
    @Autowired
    private EntityManager entityManager;



    //EVENT CONTROLLERS//
    @GetMapping("/events")
    public String tablon(HttpServletRequest request, Model model){
        model.addAttribute("anuncios",eventService.findAll());
        return "tablon";
    }

    @GetMapping("/events/{cod}")
    public String uniqueEvent(HttpServletRequest request, Model model, @PathVariable String cod){

        if (eventService.unique(cod).isPresent()) {
            model.addAttribute("event", eventService.unique(cod).get());
            return "showEvent";
        }
        else{
            return "error";
        }
    }

    @PostMapping("/events/new")
    public String newEvent(HttpServletRequest request,Model model  , Event event){
        if (eventService.unique(event.getCod()).isEmpty()){
            PolicyFactory policy= Sanitizers.FORMATTING.and(Sanitizers.LINKS);
            event.setDescription(policy.sanitize(event.getDescription()));
            eventService.addEvent(event);
            model.addAttribute("event",eventService.unique(event.getCod()).get());
            return "showEvent";
        } else {
            return "error";
        }
    }

    @PostMapping("/events/update")
    public String updateEvent(HttpServletRequest request, Model model, Event e){

        if(eventService.unique(e.getCod()).isPresent()) {
            model.addAttribute("event",eventService.updateEvent(e));
            return "showEvent";
        }
        else{
            return "error";
        }
    }
    @PostMapping("/events/delete")
    public String deleteEvent(HttpServletRequest request, Model model, Event e){

        if (eventService.deleteEvent(e.getCod())){
        model.addAttribute("anuncios",eventService.findAll());
        return "tablon";}
        else{
            return "error";
        }
    }

    //TICKET CONTROLLERS//
    @PostMapping("/events/{cod}/newTicket")
    public String newTicket(HttpServletRequest request, Model model, @PathVariable String cod, Ticket e){
        var sec = SecurityContextHolder.getContext().getAuthentication();;
        var username = sec.getName();
        e.setDatos(username);
        if (eventService.addTicket(cod,e)!=null&&eventService.unique(cod).isPresent()&&userService.existsUserByUsername(username).isPresent()&&ticketService.alreadyExist(e.getDatos()).isEmpty()) {
             userService.existsUserByUsername(username).get().getTicketsList().add(e);
            userService.existsUserByUsername(username).get().getEventsList().add(eventService.unique(cod).get());
            ticketService.addTicket(e);
            model.addAttribute("event",eventService.unique(cod).get());
            model.addAttribute("ticket",e);
            return "showTicket";
        }
        else{
            return "error";
        }
    }

    @PostMapping("/checkTicket")
    public String searchTicket(HttpServletRequest request, Model model, Ticket e){
     if (ticketService.returnTicket((int) e.getId()).isPresent()){
         model.addAttribute("event",eventService.findbyticket(ticketService.returnTicket((int) e.getId()).get()));
         model.addAttribute("ticket",ticketService.returnTicket((int) e.getId()).get());
         return "showTicket";
     }
     else{
         return "error";
     }
    }

    //ERROR DEFAULT PAGE//
    @GetMapping("/error")
    public String errorMapping(HttpServletRequest request, Model model){

        return "error";
    }

    ///BUSQUEDA DE EVENTOS
    @GetMapping("/events/query/")
    public String dynamicquery(HttpServletRequest request, Model model,String query){

        model.addAttribute("anuncios",eventService.dynamicquery(query));
        return "searchEvent";
    }

    @GetMapping("/events/querybygender/")
    public String dynamicquerygender(HttpServletRequest request, Model model,String gender){

        model.addAttribute("anuncios",eventService.dynamicquerygender(gender));
        return "searchEvent";
    }





}
