package org.example.gestion_salle_de_classe.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Occuper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "codeprof")
    private Prof prof;
    @ManyToOne
    @JoinColumn(name = "codesal")
    private Salle salle;
    @Temporal(TemporalType.DATE)
    private Date date;

    public Occuper() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Prof getProf() {
        return prof;
    }

    public void setProf(Prof prof) {
        this.prof = prof;
    }

    public Occuper(int id, Date date, Salle salle, Prof prof) {
        this.id = id;
        this.date = date;
        this.salle = salle;
        this.prof = prof;
    }
}
