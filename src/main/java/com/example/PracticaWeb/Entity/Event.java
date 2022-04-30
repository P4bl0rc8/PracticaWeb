package com.example.PracticaWeb.Entity;


import com.example.PracticaWeb.Security.Filter.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Event")
@Table(name = "Events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.Admin.class)
    private long id;

    @Column(nullable = false)
    @JsonView(View.User.class)
    private String name;
    @Column(nullable = false)
    @JsonView(View.User.class)
    private String cod;
    @Column(nullable = false)
    @JsonView(View.User.class)
    private String date;
    @Column(nullable = false)
    @JsonView(View.User.class)
    private String gender;
    @Column(nullable = false)
    @JsonView(View.User.class)
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonView(View.Admin.class)
    private List<Ticket> soldTickets = new ArrayList<>();


    public Event (String name, String cod, String date, String gender, String description){
        this.name = name;
        this.cod = cod;
        this.date = date;
        this.gender = gender;
        this.description = description;
    }
    public Event(Event e){
        this.description=e.description;
        this.name=e.name;
        this.id=e.id;
        this.date=e.date;
        this.gender=e.gender;
        this.cod=e.cod;
        this.soldTickets=new ArrayList<>();
    }
    /*
    private static long cont=0;



    public String getCod() {
        return cod;
    }

    public Map<String, Entrada> getSold() { return sold;}

    public Entrada addTicket(Entrada e){
        String id=this.cod.concat(String.valueOf("-"+cont));
        cont++;
        Entrada t= new Entrada(id,e.getDatos());
        sold.put(t.getId(),t);
        return t;
    }
    public Entrada getTicket(String id){
        return this.sold.get(id);
    }

    */

    //GETTERS && SETTERS
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @JsonIgnore
    public List<Ticket> getSoldTickets(){
        return this.soldTickets;
    }
    @JsonIgnore
    public void setSoldTickets(List<Ticket> sold) {
        this.soldTickets = sold;
    }


    public long getId(){
        return this.id;
    }


}
