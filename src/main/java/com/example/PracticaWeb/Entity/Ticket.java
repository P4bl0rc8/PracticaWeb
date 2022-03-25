package com.example.PracticaWeb.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Ticket")
@Table(name = "Tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //private String id;

    @Column(nullable = false)
    private final static int precio=10;
    @Column(nullable = false)
    private String datos;

    @ManyToOne
    private Event event;

    @ManyToOne
    private User user;

    public Ticket(String datos){ this.datos=datos;}

    //GETTERS && SETTERS
    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return this.id;
    }
    //TICKET ID NOT MODIFIABLE
}
