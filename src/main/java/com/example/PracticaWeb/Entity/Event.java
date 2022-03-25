package com.example.PracticaWeb.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
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
    private long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String cod;
    @Column(nullable = false)
    private String date;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = true)
    private String description;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Ticket> soldTickets = new ArrayList<>();

    @ManyToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<User> eventAttendance = new ArrayList<>();

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

    public String getDescripcion() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Ticket> getSold(){
        return this.soldTickets;
    }

    public void setSold(List<Ticket> sold) {
        this.soldTickets = sold;
    }

    public List<User> getAttendance(){
        return this.eventAttendance;
    }

    public long getId(){
        return this.id;
    }


}
