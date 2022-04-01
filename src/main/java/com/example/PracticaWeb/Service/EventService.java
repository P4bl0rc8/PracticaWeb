package com.example.PracticaWeb.Service;

import com.example.PracticaWeb.Entity.Event;
import com.example.PracticaWeb.Entity.Ticket;
import com.example.PracticaWeb.Entity.User;
import com.example.PracticaWeb.Repository.EventRepository;
import com.example.PracticaWeb.Repository.TicketRepository;
import com.example.PracticaWeb.Repository.UserRepository;
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
    @Autowired
    UserRepository userRepository;

    public void addEvent(Event e){
        eventRepository.save(e);
    }
    public Event updateEvent(Event e){
        Optional<Event> aux = eventRepository.findEventByCod(e.getCod());
        if (aux.isPresent()){
           aux.get().setDate(e.getDate());
           aux.get().setDescription(e.getDescription());
           aux.get().setGender(e.getGender());
           aux.get().setName(e.getName());
            eventRepository.save(aux.get());
            return aux.get();
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
    public boolean deleteEvent(String cod){
        if (eventRepository.findEventByCod(cod).isPresent()){
            Event e=eventRepository.findEventByCod(cod).get();
            //DESVINCULAR ENTRADASYEVENTO-DEL USER
            List<Ticket> aux=e.getSoldTickets();
            List<User> aux2=userRepository.findAll();
            for (User u: aux2){
                u.getTicketsList().removeIf(aux::contains);
                u.getEventsList().remove(e);
                userRepository.save(u);
            }
            eventRepository.delete(e);
            return true;
        }
        else{
            return false;
        }
    }
    public Collection<Event> findAll(){
        return eventRepository.findAll();
    }

    public Optional<Event> unique(String cod){
        return eventRepository.findEventByCod(cod);
    }
}