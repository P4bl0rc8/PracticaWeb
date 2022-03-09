package com.example.PracticaWeb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    private String username;
    private String email;
    private String password;

    public String getPass(){ return password;}

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

}
