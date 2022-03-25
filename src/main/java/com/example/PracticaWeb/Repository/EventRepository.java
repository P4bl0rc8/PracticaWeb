package com.example.PracticaWeb.Repository;

import com.example.PracticaWeb.Entity.Event;
import com.example.PracticaWeb.Entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {

    public Optional<Event> findEventById(long id);
    public Optional<Event> findEventByCod(String cod);
    public Optional<Event> findEventByName(String name);
    public List<Event> findEventByDate(String date);
    public List<Event> findEventByGender(String gender);
    public List<Ticket> findTicketsByName(Event event);
}
