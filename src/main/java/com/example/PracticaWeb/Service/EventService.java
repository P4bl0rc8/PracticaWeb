package com.example.PracticaWeb.Service;

import com.example.PracticaWeb.Entity.Event;
import com.example.PracticaWeb.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;

    public void addEvent(Event e){
        eventRepository.save(e);
    }

    public Collection<Event> findAll(){
        return eventRepository.findAll();
    }

    public Optional<Event> unique(String cod){
        return eventRepository.findEventByCod(cod);
    }
}
