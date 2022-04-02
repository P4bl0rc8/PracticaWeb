package com.example.PracticaWeb.Controller.Web;


import com.example.PracticaWeb.Entity.Event;
import com.example.PracticaWeb.Entity.Ticket;
//import com.example.PracticaWeb.EventHolder;
import com.example.PracticaWeb.Service.EventService;
import com.example.PracticaWeb.Service.TicketService;
import com.example.PracticaWeb.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class WebController {
    @Autowired
    TicketService ticketService;
    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;

    //EVENT CONTROLLERS//
    @GetMapping("/events")
    public String tablon(Model model){
        model.addAttribute("anuncios",eventService.findAll());
        return "tablon";
    }

    @GetMapping("/events/{cod}")
    public String uniqueEvent(Model model, @PathVariable String cod){
        if (eventService.unique(cod).isPresent()) {
            model.addAttribute("event", eventService.unique(cod).get());
            return "evento";
        }
        else{
            return "error";
        }
    }
    @PostMapping("/events/new")
    public String newEvent(Model model, Event event){
        eventService.unique(event.getCod());
        if (eventService.unique(event.getCod()).isEmpty()) {
            eventService.addEvent(event);
            model.addAttribute("event",eventService.unique(event.getCod()).get());
            return "evento";
        } else {
            return "error";
        }
    }

    @PostMapping("/events/update")
    public String updateEvent(Model model, Event e){
        if(eventService.unique(e.getCod()).isPresent()) {
            model.addAttribute("event",eventService.updateEvent(e));
            return "evento";
        }
        else{
            return "error";
        }
    }
    @PostMapping("/events/delete")
    public String deleteEvent(Model model, Event e){
        if (eventService.deleteEvent(e.getCod())){
        model.addAttribute("anuncios",eventService.findAll());
        return "tablon";}
        else{
            return "error";
        }
    }

    //TICKET CONTROLLERS//
    @PostMapping("/events/{cod}/newTicket")
    public String newTicket(Model model, @PathVariable String cod, Ticket e){
        if (eventService.addTicket(cod,e)!=null&&eventService.unique(cod).isPresent()&&userService.existsUserByEmail(e.getDatos()).isPresent()) {
            userService.existsUserByEmail(e.getDatos()).get().getTicketsList().add(e);
            userService.existsUserByEmail(e.getDatos()).get().getEventsList().add(eventService.unique(cod).get());
            ticketService.addTicket(e);
            model.addAttribute("event",eventService.unique(cod).get());
            model.addAttribute("ticket",e);
            return "showticket";
        }
        else{
            return "error";
        }
    }
    /*
    @PostMapping("/events/{cod}/checkTicket")
    public String searchTicket(Model model, @PathVariable String cod, Ticket e){
     if (ticketService.returnTicket((int) e.getId()).isPresent()&&eventService.unique(cod).isPresent()){
         model.addAttribute("event",eventService.unique(cod).get());
         model.addAttribute("ticket",ticketService.returnTicket((int) e.getId()).get());
         return "showticket";
     }
     else{
         return "error";
     }
    }
    */
    //ERROR DEFAULT PAGE//
    @GetMapping("/error")
    public String errorMapping(Model model){
        return "error";
    }
}
