package com.example.PracticaWeb.Controller.Web;


import com.example.PracticaWeb.Entity.Event;
import com.example.PracticaWeb.Entity.Ticket;
import com.example.PracticaWeb.EventHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {

    @Autowired
    EventHolder eventHolder;

    //EVENT CONTROLLERS//
    @GetMapping("/events")
    public String tablon(Model model){
        model.addAttribute("anuncios",eventHolder.eventoCollection());
        return "tablon";
    }

    @GetMapping("/events/{cod}")
    public String uniqueEvent(Model model, @PathVariable String cod){

        model.addAttribute("evento",eventHolder.unique(cod));
        return "evento";

    }
    @PostMapping("/events/new")
    public String newEvent(Model model, Event event){
        Event aux = eventHolder.unique(event.getCod());
        if (aux == null) {
            eventHolder.addEvent(event);
            model.addAttribute("evento",eventHolder.unique(event.getCod()));
            return "evento";
        } else {
            return "error";
        }
    }

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
        }*/
    }

    //ERROR DEFAULT PAGE//
    @GetMapping("/error")
    public String errorMapping(Model model){
        return "error";
    }


}
