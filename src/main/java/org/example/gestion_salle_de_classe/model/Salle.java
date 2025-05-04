package org.example.gestion_salle_de_classe.model;

import javax.persistence.*;

@Entity
public class Salle {
    @Id
    private int codesal;
    private String designation;

    public Salle() {
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;

    }
    public int getCodesal() {
        return codesal;
    }

    public void setCodesal(int codesal) {
        this.codesal = codesal;
    }

    public Salle(int codesal, String designation) {
        this.codesal = codesal;
        this.designation = designation;
    }
}