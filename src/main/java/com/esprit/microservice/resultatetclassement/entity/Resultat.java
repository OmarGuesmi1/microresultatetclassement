package com.esprit.microservice.resultatetclassement.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "results")
public class Resultat {

    @Id
    private String id;

    private String idMatch;
    private String idCompetition;
    private String idClubDomicile;
    private String idClubExterieur;

    private int butsDomicile;
    private int butsExterieur;
    private VainqueurMatch vainqueur; // Enum : DOMICILE, EXTERIEUR, ÉGALITÉ
    private LocalDate date;

    // 🧱 Constructeur vide
    public Resultat() {}

    // 🧱 Constructeur avec tous les champs
    public Resultat(String id, String idMatch, String idCompetition, String idClubDomicile, String idClubExterieur,
                    int butsDomicile, int butsExterieur, VainqueurMatch vainqueur, LocalDate date) {
        this.id = id;
        this.idMatch = idMatch;
        this.idCompetition = idCompetition;
        this.idClubDomicile = idClubDomicile;
        this.idClubExterieur = idClubExterieur;
        this.butsDomicile = butsDomicile;
        this.butsExterieur = butsExterieur;
        this.vainqueur = vainqueur;
        this.date = date;
    }

    // 🧩 Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(String idMatch) {
        this.idMatch = idMatch;
    }

    public String getIdCompetition() {
        return idCompetition;
    }

    public void setIdCompetition(String idCompetition) {
        this.idCompetition = idCompetition;
    }

    public String getIdClubDomicile() {
        return idClubDomicile;
    }

    public void setIdClubDomicile(String idClubDomicile) {
        this.idClubDomicile = idClubDomicile;
    }

    public String getIdClubExterieur() {
        return idClubExterieur;
    }

    public void setIdClubExterieur(String idClubExterieur) {
        this.idClubExterieur = idClubExterieur;
    }

    public int getButsDomicile() {
        return butsDomicile;
    }

    public void setButsDomicile(int butsDomicile) {
        this.butsDomicile = butsDomicile;
    }

    public int getButsExterieur() {
        return butsExterieur;
    }

    public void setButsExterieur(int butsExterieur) {
        this.butsExterieur = butsExterieur;
    }

    public VainqueurMatch getVainqueur() {
        return vainqueur;
    }

    public void setVainqueur(VainqueurMatch vainqueur) {
        this.vainqueur = vainqueur;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
