package com.example.PracticaWeb.Service;

import com.example.PracticaWeb.Entity.Event;
import com.example.PracticaWeb.Entity.Ticket;
import com.example.PracticaWeb.Entity.User;
import com.example.PracticaWeb.Repository.EventRepository;
import com.example.PracticaWeb.Repository.TicketRepository;
import com.example.PracticaWeb.Repository.UserRepository;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
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
    @Autowired
    EntityManager entityManager;
    //EVENT FUNCTIONALITY//
    public void addEvent(Event e){
        eventRepository.save(e);
    }
    public Event updateEvent(Event e){
        Optional<Event> aux = eventRepository.findEventByCod(e.getCod());
        if (aux.isPresent()){
           aux.get().setDate(e.getDate());
            PolicyFactory policy= Sanitizers.FORMATTING.and(Sanitizers.LINKS);
            aux.get().setDescription(policy.sanitize(e.getDescription()));
           aux.get().setGender(e.getGender());
           aux.get().setName(e.getName());
            eventRepository.save(aux.get());
            return aux.get();
        }
        else{
            return null;
        }
    }
    public boolean deleteEvent(String cod){
        if (eventRepository.findEventByCod(cod).isPresent()){
            Event e=eventRepository.findEventByCod(cod).get();
            List<Ticket> aux=e.getSoldTickets();
            List<User> aux2=userRepository.findAllByEventsListContains(e);
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
    public Collection<Event> parser(Collection<Event> c){
        Collection<Event> parsed = new ArrayList<>();
        for (Event e:c){
            Event aux = new Event(e);
            parsed.add(aux);
        }
        return parsed;
    }
    public Optional<Event> unique(String cod){
        return eventRepository.findEventByCod(cod);
    }

    //TICKET FUNCTIONALITY//
    public Ticket addTicket(String cod,Ticket e){
        if (eventRepository.findEventByCod(cod).isPresent()){
            eventRepository.findEventByCod(cod).get().getSoldTickets().add(e);
            return e;
        }
        else{
            return null;
        }
    }
    public Optional<Ticket> getTicket(String cod, long ticket_id){
        if (eventRepository.findEventByCod(cod).isPresent()&&ticketRepository.findTicketByid(ticket_id).isPresent()) {
            if (eventRepository.findEventByCod(cod).get().getSoldTickets().contains(ticketRepository.findTicketByid(ticket_id).get())) {
                return ticketRepository.findTicketByid(ticket_id);
            } else {
                return Optional.empty();
            }
        }else{
            return Optional.empty();
        }
    }
    public Collection<Ticket> allTickets(String cod){
        if(eventRepository.findEventByCod(cod).isPresent()){
            return eventRepository.findEventByCod(cod).get().getSoldTickets();
        }else{
            return null;
        }
    }

    public Optional<Event> findbyticket(Ticket e){
        return eventRepository.findEventBySoldTicketsContains(e);
    }

    public Collection<Event> dynamicquery(String name){
        TypedQuery<Event> typedQuery= entityManager.createQuery("SELECT e FROM Event e where e.name=:name ",Event.class);
        typedQuery.setParameter("name",name);
        return typedQuery.getResultList();
    }
    public Collection<Event> dynamicquerygender(String gender){
        TypedQuery<Event> typedQuery= entityManager.createQuery("SELECT e FROM Event e where e.gender=:gender ",Event.class);
        typedQuery.setParameter("gender",gender);
        return typedQuery.getResultList();
    }
}
