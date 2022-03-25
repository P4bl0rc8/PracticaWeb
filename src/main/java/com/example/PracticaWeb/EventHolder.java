/*
package com.example.PracticaWeb;



import com.example.PracticaWeb.Entity.Event;
import com.example.PracticaWeb.Entity.Ticket;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EventHolder {


    private Map<String, Event> events = new ConcurrentHashMap<>();


    public void addEvent(Event e){
        events.put(e.getCod(),e);
    }
    public void addEvent(Event e, String cod){
        events.put(cod,e);
    }
    public Ticket addTicket(String cod, Ticket e){
        Event aux=events.get(cod);
        Ticket t;
        if (aux!=null){

        }else{
            t=null;
        }
        return t;
    }

    public Collection<Event> eventoCollection(){
        Collection<Event> aux=events.values();
        return aux;
    }

    public Event unique(String cod){
        Event aux=events.get(cod);
        if (aux!=null){
            return aux;
        }else{
            return null;
        }
    }
    public Event remove(String cod){
        return events.remove(cod);
    }

    public Event switchinTickets(String cod, Event e){
        if (events.get(cod)!=null){
            e.setSold(events.get(cod).getSold());
        }
        return e;
    }
    public Ticket getTicket(String cod, String id){
        Ticket t;
        if (events.get(cod)!=null){

        }else{
            t=null;
        }
        return t;
    }

    public Collection<Ticket> alltickets(String cod){
        if(events.get(cod)!=null){
            return aux;
        }
        return null;
    }
}
*/