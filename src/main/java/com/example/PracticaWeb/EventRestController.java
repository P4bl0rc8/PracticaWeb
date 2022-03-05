package com.example.PracticaWeb;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class EventRestController {
    @Autowired
    EventHolder eventHolder;
    //CREATE EVENT
    @PostMapping("/events")
    @ResponseStatus(HttpStatus.CREATED)
    public Evento newEvent (@RequestBody Evento event){
        eventHolder.addEvent(event);
        return event;
    }
    //RETURN ALL EVENTS
    @GetMapping("/events")
    public Collection<Evento> allevents(){
        return eventHolder.eventoCollection();
    }
    //RETURN ONE EVENT SPECIFYING ITS CODE
    @GetMapping("/events/{cod}")
    public ResponseEntity<Evento> uniqueevent(@PathVariable String cod){
       if(eventHolder.unique(cod)!=null){
           return new ResponseEntity<>(eventHolder.unique(cod), HttpStatus.OK);
       }else{
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }

    }

    //DELETE ONE EVENT
    @DeleteMapping("/events/{cod}")
    public ResponseEntity<Evento> removeevent(@PathVariable String cod){
        Evento aux=eventHolder.remove(cod);
        if(aux!=null){
            return new ResponseEntity<>(aux,HttpStatus.OK);

        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //UPDATE ONE EVENT ALREADY CREATED BUT IT DOESNT CHANGE SOLD TICKETS STRUCTURE.
    @PutMapping("/events/{cod}")
    public ResponseEntity<Evento> updateevent(@PathVariable String cod,@RequestBody Evento updated){
        Evento aux=eventHolder.unique(cod);
        if(aux!=null){
            eventHolder.addEvent(eventHolder.switchinTickets(cod,updated),cod);
            return new ResponseEntity<>(updated,HttpStatus.OK);

        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    ////CREATING AND SHOWING TICKETS, TICKETS ARE PERMANENT, YOU SHOULDNT BE ABLE TO DESTROY OR MODIFY THEM
    @PostMapping("/events/{cod}/ticket")
    public ResponseEntity<Entrada> creatingTicket(@PathVariable String cod){
        if(eventHolder.unique(cod)!=null){
            return new ResponseEntity<>(eventHolder.addTicket(cod), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    ///SHOWING AN UNIQUE TICKET KNOWING THE EVENT AND CODE
    @GetMapping("/events/{cod}/ticket/{id}")
    public ResponseEntity<Entrada> uniqueticket(@PathVariable String cod,@PathVariable String id){
        if(eventHolder.getTicket(cod,id)!=null){
            return new ResponseEntity<>(eventHolder.getTicket(cod,id), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/events/{cod}/alltickets")
    public Collection<Entrada> alltickets(@PathVariable String cod){
        return eventHolder.alltickets(cod);
    }














}
