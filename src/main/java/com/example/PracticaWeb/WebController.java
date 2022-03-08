package com.example.PracticaWeb;


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

    @GetMapping("/events")
    public String tablon(Model model){
        model.addAttribute("anuncios",eventHolder.eventoCollection());
        return "tablon";
    }

    @GetMapping("/events/{cod}")
    public String unicoevento(Model model, @PathVariable String cod){

        model.addAttribute("evento",eventHolder.unique(cod));
        return "evento";

    }
    @PostMapping("/events/new")
    public String newevent(Model model,Evento evento){
        eventHolder.addEvent(evento);
        model.addAttribute("evento",eventHolder.unique(evento.getCod()));
        return "evento";
    }

    @PostMapping("/events/{cod}/newticket")
    public String crearentrada(Model model,@PathVariable String cod,Entrada e){
        model.addAttribute("evento",eventHolder.unique(cod));
        model.addAttribute("ticket",eventHolder.addTicket(cod,e));
        return "showticket";
    }


}
