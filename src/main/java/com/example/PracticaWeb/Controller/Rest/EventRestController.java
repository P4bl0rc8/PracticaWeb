package com.example.PracticaWeb.Controller.Rest;

import com.example.PracticaWeb.Service.*;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.PracticaWeb.Entity.*;


import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EventRestController{
    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;
    @Autowired
    TicketService ticketService;

    //CREATE EVENT//
    @PostMapping("/events")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Event> newEvent (@RequestBody Event event){
        if (eventService.unique(event.getCod()).isEmpty()){
            PolicyFactory policy= Sanitizers.FORMATTING.and(Sanitizers.LINKS);
            event.setDescription(policy.sanitize(event.getDescription()));
            eventService.addEvent(event);
            return new ResponseEntity<>(event, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    //RETURN ALL EVENTS//
    @GetMapping("/events")
    public Collection<Event> allEvents(){
        return eventService.findAll();
    }
    //RETURN ONE EVENT SPECIFYING ITS CODE
    @GetMapping("/events/{cod}")
    public ResponseEntity<Event> uniqueEvent(@PathVariable String cod){
       if(eventService.unique(cod).isPresent()){
           return new ResponseEntity<>(eventService.unique(cod).get(), HttpStatus.OK);
       }else{
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }

    }

    //DELETE ONE EVENT//
    @DeleteMapping("/events/{cod}")
    public ResponseEntity<Event> removeEvent(@PathVariable String cod){
        if(eventService.unique(cod).isPresent()){
            Event aux = eventService.unique(cod).get();
            eventService.deleteEvent(cod);
            return new ResponseEntity<>(aux, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //UPDATE ONE EVENT ALREADY CREATED BUT IT DOESNT CHANGE SOLD TICKETS STRUCTURE//
    @PutMapping("/events/{cod}")
    public ResponseEntity<Event> updateEvent(@PathVariable String cod, @RequestBody Event updated){
        if(eventService.unique(cod).isPresent()){
            eventService.updateEvent(updated);
            return new ResponseEntity<>(eventService.unique(cod).get(),HttpStatus.OK);

        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //CREATING AND SHOWING TICKETS//
    @PostMapping("/events/{cod}/ticket")
    public ResponseEntity<Ticket> createTicket(@PathVariable String cod, @RequestBody Ticket entrada){
        if (eventService.addTicket(cod,entrada)!=null&&eventService.unique(cod).isPresent()&&userService.existsUserByEmail(entrada.getDatos()).isPresent()) {
            userService.existsUserByEmail(entrada.getDatos()).get().getTicketsList().add(entrada);
            userService.existsUserByEmail(entrada.getDatos()).get().getEventsList().add(eventService.unique(cod).get());
            ticketService.addTicket(entrada);
            return  new ResponseEntity<>(eventService.getTicket(cod, entrada.getId()).get(),HttpStatus.OK);
        }
        else{
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    //SHOWING AN UNIQUE TICKET KNOWING THE EVENT AND ID//
    @GetMapping("/events/{cod}/ticket/{id}")
    public ResponseEntity<Optional<Ticket>> uniqueTicket(@PathVariable String cod, @PathVariable long id){
        if(eventService.unique(cod).isPresent()){
            return new ResponseEntity<>(eventService.getTicket(cod,id), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //SHOWING ALL TICKETS KNOWING THE EVENT CODE//
    @GetMapping("/events/{cod}/alltickets")
    public Collection<Ticket> allTickets(@PathVariable String cod){
        return eventService.allTickets(cod);
    }














}
