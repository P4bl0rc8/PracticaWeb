package com.example.PracticaWeb;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @Autowired
    EventHolder eventHolder;

    @GetMapping("/events")
    public String tablon(Model model){
        model.addAttribute("anuncios",eventHolder.eventoCollection());
        return "tablon";
    }

}
