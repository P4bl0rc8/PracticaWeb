package com.example.PracticaWeb.Controller.Web;


import com.example.PracticaWeb.Entity.Event;
import com.example.PracticaWeb.Entity.Ticket;
//import com.example.PracticaWeb.EventHolder;
import com.example.PracticaWeb.Service.EventService;
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
    EventService eventService;

    //EVENT CONTROLLERS//
    @GetMapping("/events")
    public String tablon(Model model){
        model.addAttribute("anuncios",eventService.findAll());
        return "tablon";
    }

    @GetMapping("/events/{cod}")
    public String uniqueEvent(Model model, @PathVariable String cod){

        model.addAttribute("event",eventService.unique(cod));
        return "evento";

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
/*
    @PostMapping("/events/update")
    public String updateEvent(Model model, Event e){
        eventHolder.switchinTickets(e.getCod(),e);
        eventHolder.addEvent(e);
        model.addAttribute("evento",eventHolder.unique(e.getCod()));
        return "evento";
    }

    @PostMapping("/events/delete")
    public String deleteEvent(Model model, Event e){
        eventHolder.remove(e.getCod());
        model.addAttribute("anuncios",eventHolder.eventoCollection());
        return "tablon";
    }

    //TICKET CONTROLLERS//
    @PostMapping("/events/{cod}/newTicket")
    public String newTicket(Model model, @PathVariable String cod, Ticket e){
        model.addAttribute("evento",eventHolder.unique(cod));
        model.addAttribute("ticket",eventHolder.addTicket(cod,e));
        return "showticket";
    }

    @PostMapping("/events/{cod}/checkTicket")
    public String searchTicket(Model model, @PathVariable String cod, Ticket e){
        //Ticket aux = eventHolder.getTicket(cod,e.getId());
        model.addAttribute("evento",eventHolder.unique(cod));
        //model.addAttribute("ticket",eventHolder.getTicket(cod,aux.getId()));
        /*if(aux!=null){
            return "showticket";
        }else{
            return "error";
        }
    }

    //ERROR DEFAULT PAGE//
    @GetMapping("/error")
    public String errorMapping(Model model){
        return "error";
    }

*/
}
