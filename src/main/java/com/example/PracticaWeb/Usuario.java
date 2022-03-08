package com.example.PracticaWeb;

public class Usuario {

    private String username;
    private String email;

    public Usuario(String username, String email){
        this.username = username;
        this.email = email;
    }
    public Usuario(){
        this.username = "";
        this.email = "";
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

}
