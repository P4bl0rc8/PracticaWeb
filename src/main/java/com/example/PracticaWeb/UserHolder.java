package com.example.PracticaWeb;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserHolder {

    private Map<String, Usuario> users = new ConcurrentHashMap<>();

    public void addUser(String username, Usuario user){users.put(username, user);}
    public void addUser(Usuario user){users.put(user.getUsername(), user);}

    public Collection<Usuario> userCollection(){
        Collection<Usuario> aux = users.values();
        return aux;
    }

    public Usuario unique(String username){
        Usuario aux = users.get(username);
        if(aux!=null){
            return aux;
        }else{
            return null;
        }
    }

}
