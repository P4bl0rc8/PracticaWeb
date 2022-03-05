package com.example.PracticaWeb;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entrada {

    private String id;
    private final static int precio=10;
    private String datos;

    public Entrada(String datos){
        this.datos=datos;
    }


    public String getId() {
        return id;
    }
}
