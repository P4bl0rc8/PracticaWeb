package com.example.PracticaWeb.Security.Filter;

import com.example.PracticaWeb.Enumerated.Role;

import java.util.HashMap;
import java.util.Map;

public class View {
    public static class User{}
    public static class Admin extends User{}

    public static final Map<Role, Class> mapping = new HashMap<>();
    static{
        mapping.put(Role.ROLE_ADMIN, Admin.class);
        mapping.put(Role.ROLE_USER, User.class);
    }
}
