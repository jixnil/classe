package org.example.gestion_salle_de_classe.model;

import javax.persistence.*;

@Entity
public class Prof {
    @Id
    private int codeprof;
    private String nom;
    private String prenom;
    private String grade;

    public Prof() {

    }

    public int getCodeprof() {
        return codeprof;
    }

    public void setCodeprof(int codeprof) {
        this.codeprof = codeprof;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Prof(int codeprof, String grade, String prenom, String nom) {
        this.codeprof = codeprof;
        this.grade = grade;
        this.prenom = prenom;
        this.nom = nom;
    }
}