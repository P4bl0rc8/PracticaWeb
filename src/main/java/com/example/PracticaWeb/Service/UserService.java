package com.example.PracticaWeb.Service;


import com.example.PracticaWeb.Entity.Ticket;
import com.example.PracticaWeb.Entity.User;
import com.example.PracticaWeb.Repository.TicketRepository;
import com.example.PracticaWeb.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TicketRepository ticketRepository;

    //CHECK IF EXISTS
    //SE PUEDE HACER QUE DEVUELVA BOOLEAN TB, COMO PREFIRAIS//
    public Optional<User> existsUserById(long id){
        var aux = userRepository.findUserById(id);
        return aux;

    }
    public Optional<User> existsUserByUsername(String username){
        var aux = userRepository.findUserByUsername(username);
        return aux;

    }
    public Optional<User> existsUserByEmail(String email){
        var aux = userRepository.findUserByEmail(email);
        return aux;

    }

    //FUNCTIONALITY USERS
    public User addUser(User user){
        return userRepository.save(user);

    }

    public Optional<User> deleteUser(long id){
        var aux = userRepository.findUserById(id);
        if(aux.isPresent()){
            userRepository.delete(aux.get());
        }
        return aux;

    }
    public Optional<User> returnUser(long id){
        return userRepository.findUserById(id);

    }
    public Collection<User> returnAllUsers(){
        return userRepository.findAll();

    }


    //FUNCTIONALITY TICKETS
    public Collection<Ticket> addTicketToUser(Ticket ticket, long id){
        Optional<User> aux = userRepository.findUserById(id);
        if(aux.isPresent()){
            var aux1 = aux.get();
            List<Ticket> ticketsList = aux1.getTicketsList();
            ticketsList.add(ticket);
            aux1.setTicketsList(ticketsList);
            userRepository.save(aux1);
            return ticketsList;
        }else{
            return null;
        }

    }
    public Ticket returnLastBought(User user){
        List<Ticket> list = ticketRepository.findTicketsByUser(user.getId());
        return list.get(list.size()-1);

    }
}
