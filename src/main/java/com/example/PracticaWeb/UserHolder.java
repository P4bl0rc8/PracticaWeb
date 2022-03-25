/*package com.example.PracticaWeb;

import com.example.PracticaWeb.Entity.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserHolder {

    private Map<String, User> users = new ConcurrentHashMap<>();

    public void addUser(String username, User user) {
        users.put(username, user);
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public Collection<User> userCollection() {
        Collection<User> aux = users.values();
        return aux;
    }

    public User unique(String username) {
        User aux = users.get(username);
        if (aux != null) {
            return aux;
        } else {
            return null;
        }
    }

    public User delete(String username) {

        return users.remove(username);

    }
}
*/
