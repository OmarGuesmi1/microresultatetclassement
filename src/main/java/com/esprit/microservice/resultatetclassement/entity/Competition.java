package com.esprit.microservice.resultatetclassement.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "competitions")
public class Competition {

    @Id
    private String id;
    private String nom;
    private TypeCompetition type; // Enum : LIGUE, COUPE, LIGUE_DES_CHAMPIONS, etc.
    private String saison;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    // 🧱 Constructeurs
    public Competition() {
    }

    public Competition(String id, String nom, TypeCompetition type, String saison, LocalDate dateDebut, LocalDate dateFin) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.saison = saison;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    // 🧩 Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public TypeCompetition getType() {
        return type;
    }

    public void setType(TypeCompetition type) {
        this.type = type;
    }

    public String getSaison() {
        return saison;
    }

    public void setSaison(String saison) {
        this.saison = saison;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }
}
