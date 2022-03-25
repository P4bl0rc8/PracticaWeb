package com.example.PracticaWeb.Service;

import com.example.PracticaWeb.Entity.Event;
import com.example.PracticaWeb.Entity.Ticket;
import com.example.PracticaWeb.Repository.EventRepository;
import com.example.PracticaWeb.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    TicketRepository ticketRepository;

    public void addEvent(Event e){
        eventRepository.save(e);
    }
    public Event updateEvent(Event e){
        Optional<Event> aux = eventRepository.findEventByCod(e.getCod());
        if (aux.isPresent()){
            List<Ticket> aux2=aux.get().getSoldTickets();
            e.setSoldTickets(aux2);
            eventRepository.delete(aux.get());
            eventRepository.save(e);
            eventRepository.findEventById(e.getId()).get().setId(aux.get().getId());
            return e;
        }
        else{
            return null;
        }
    }
    public Ticket addTicket(String cod,Ticket e){
        if (eventRepository.findEventByCod(cod).isPresent()){
            eventRepository.findEventByCod(cod).get().getSoldTickets().add(e);
            return e;
        }
        else{
            return null;
        }
    }
    public Collection<Event> findAll(){
        return eventRepository.findAll();
    }

    public Optional<Event> unique(String cod){
        return eventRepository.findEventByCod(cod);
    }
}
