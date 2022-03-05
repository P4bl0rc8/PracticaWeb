package com.example.PracticaWeb;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Evento {

    private String nombre;
    private String cod;
    private String fecha;
    private String genero;
    private String descripcion;
    private Map<String,Entrada> sold=new ConcurrentHashMap<>();
    private static long cont=0;

    public String getCod() {
        return cod;
    }

    public Map<String, Entrada> getSold() {
        return sold;
    }

    public Entrada addTicket(Entrada e){
        String id=this.cod.concat(String.valueOf(cont));
        cont++;
        Entrada t= new Entrada(id,e.getDatos());
        sold.put(t.getId(),t);
        return t;
    }
    public Entrada getTicket(String id){
        return this.sold.get(id);
    }


}
