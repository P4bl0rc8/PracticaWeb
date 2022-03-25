package com.example.PracticaWeb.Repository;

import com.example.PracticaWeb.Entity.Ticket;
import com.example.PracticaWeb.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {


    public List<Ticket> findTicketsByUser(long id);
   // public List<Ticket> findTicketsByUsername(String username);
    public Optional<Ticket> findTicketByid(long Id);
    public List<Ticket> findTicketsByEvent(long id);

}
