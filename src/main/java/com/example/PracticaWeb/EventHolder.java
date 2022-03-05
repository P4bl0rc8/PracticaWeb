package com.example.PracticaWeb;


import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EventHolder {

    private Map<String,Evento> events = new ConcurrentHashMap<>();


    public void addEvent(Evento e){
        events.put(e.getCod(),e);
    }
    public void addEvent(Evento e,String cod){
        events.put(cod,e);
    }
    public Entrada addTicket(String cod,Entrada e){
        Evento aux=events.get(cod);
        Entrada t;
        if (aux!=null){
           t = events.get(cod).addTicket(e);
        }else{
            t=null;
        }
        return t;
    }

    public Collection<Evento> eventoCollection(){
        Collection<Evento> aux=events.values();
        return aux;
    }

    public Evento unique(String cod){
        Evento aux=events.get(cod);
        if (aux!=null){
            return aux;
        }else{
            return null;
        }
    }
    public Evento remove(String cod){
        return events.remove(cod);
    }

    public Evento switchinTickets(String cod,Evento e){
        if (events.get(cod)!=null){
            e.setSold(events.get(cod).getSold());
        }
        return e;
    }
    public Entrada getTicket(String cod,String id){
        Entrada t;
        if (events.get(cod)!=null){
            t = events.get(cod).getTicket(id);
        }else{
            t=null;
        }
        return t;
    }

    public Collection<Entrada> alltickets(String cod){
        if(events.get(cod)!=null){
            Collection<Entrada> aux =events.get(cod).getSold().values();
            return aux;
        }
        return null;
    }
}
