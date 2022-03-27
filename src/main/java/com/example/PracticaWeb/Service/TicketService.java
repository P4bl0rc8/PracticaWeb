package com.example.PracticaWeb.Service;

import com.example.PracticaWeb.Entity.Event;
import com.example.PracticaWeb.Entity.Ticket;
import com.example.PracticaWeb.Entity.User;
import com.example.PracticaWeb.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    //CHECK IF EXISTS
    public Optional<Ticket> existsById(Integer id){
        var aux = ticketRepository.findTicketByid(id);
        return aux;

    }
    /*
    public boolean existsByUsername(String username){
        List<Ticket> aux = ticketRepository.findTicketsByUser(username);
        return aux.isEmpty();

    }*/
    /*
    public boolean existsByUser(User user){
        List<Ticket> aux = ticketRepository.findTicketsByUser(user.getId());
        return aux.isEmpty();

    }*/

    //FUNCTIONALITY
    public Ticket addTicket(Ticket ticket){
        return ticketRepository.save(ticket);

    }
    public Optional<Ticket> deleteTicket(Integer id){
        Optional<Ticket> aux = ticketRepository.findTicketByid(id);
        if(aux.isPresent()){
            ticketRepository.delete(aux.get());
        }
        return aux;

    }
    public Optional<Ticket> returnTicket(Integer id){
        return ticketRepository.findTicketByid(id);

    }
    /*
    public Collection<Ticket> returnAllUserTickets(User user){
        return ticketRepository.findTicketsByUser(user.getId());

    }

    public Collection<Ticket> returnAllUsernameTickets(User user){
        return ticketRepository.findTicketsByUsername(user.getUsername());

    }
    public Collection<Ticket> returnSoldTickets(Event event){
        return ticketRepository.findTicketsByEvent(event.getId());

    }
    public Collection<Ticket> returnAllSoldTickets(){
        return ticketRepository.findAll();

    }*/
}
