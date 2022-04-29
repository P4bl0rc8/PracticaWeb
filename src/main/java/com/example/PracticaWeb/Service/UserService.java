package com.example.PracticaWeb.Service;


import com.example.PracticaWeb.Entity.Event;
import com.example.PracticaWeb.Entity.Ticket;
import com.example.PracticaWeb.Entity.User;
import com.example.PracticaWeb.Enumerated.Role;
import com.example.PracticaWeb.Repository.EventRepository;
import com.example.PracticaWeb.Repository.TicketRepository;
import com.example.PracticaWeb.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    EventRepository eventRepository;

    //CHECK IF EXISTS
    //SE PUEDE HACER QUE DEVUELVA BOOLEAN TB, COMO PREFIRAIS//
    public Optional<User> existsUserById(long id) {
        return userRepository.findUserById(id);

    }

    public Optional<User> existsUserByUsername(String username) {
        return userRepository.findUserByUsername(username);

    }

    public Optional<User> existsUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    //FUNCTIONALITY USERS//
    public User addUser(User user) {
        BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public boolean deleteUser(String username){

        if(userRepository.findUserByUsername(username).isPresent()){
            User aux = userRepository.findUserByUsername(username).get();
            //UNLINK TICKET/USER FROM EVENT
            List<Ticket> auxlist = aux.getTicketsList();
            List<Event> auxEvent = eventRepository.findAll();
            for(Event event : auxEvent){
                event.getSoldTickets().removeIf(auxlist::contains);
                aux.getEventsList().remove(event);
                eventRepository.save(event);
            }
            userRepository.delete(aux);
            return true;
        }
        else{
            return false;
        }
    }

    public Optional<User> returnUser(long id) {
        return userRepository.findUserById(id);

    }

    public Collection<User> returnAllUsers() {
        return userRepository.findAll();

    }

    /*
    public Ticket returnLastBought(User user){
        List<Ticket> list = ticketRepository.findTicketsByUser(user.getId());
        return list.get(list.size()-1);

    }*/

    public User updateUser(User user){

        Optional<User> aux = userRepository.findUserByEmail(user.getEmail());
        if(aux.isPresent()){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            aux.get().setUsername(user.getUsername());
            aux.get().setPassword(encoder.encode(user.getPassword()));
            return userRepository.save(aux.get());
        }else{
            return null;
        }
    }

    public Role hasRole(String user){
        try{
        return userRepository.findUserByUsername(user).get().getRole();}
        catch (NoSuchElementException exception){
            return null;
        }
    }

    public boolean equalsUser(User user, String username){
        return user.getUsername().equals(username) && user.getPassword().equals(userRepository.findUserByUsername(username).get().getPassword())
                && user.getEmail().equals(userRepository.findUserByUsername(username).get().getEmail()) && user.getAuthorities().equals(userRepository.findUserByUsername(username).get().getAuthorities());
    }
}