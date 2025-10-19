package com.esprit.microservice.resultatetclassement.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "matches")
public class Match {

    @Id
    private String id;

    private String idCompetition;
    private String idClubDomicile;
    private String idClubExterieur;

    private int butsDomicile;
    private int butsExterieur;
    private LocalDate dateMatch;

    private StatutMatch statut; // PROGRAMMÉ, TERMINÉ, ANNULÉ
    private VainqueurMatch vainqueur; // DOMICILE, EXTERIEUR, ÉGALITÉ

    private String stade;
    private boolean domicile;

    // 🧱 Constructeurs
    public Match() {
    }

    public Match(String id, String idCompetition, String idClubDomicile, String idClubExterieur,
                 int butsDomicile, int butsExterieur, LocalDate dateMatch,
                 StatutMatch statut, VainqueurMatch vainqueur,
                 String stade, boolean domicile) {
        this.id = id;
        this.idCompetition = idCompetition;
        this.idClubDomicile = idClubDomicile;
        this.idClubExterieur = idClubExterieur;
        this.butsDomicile = butsDomicile;
        this.butsExterieur = butsExterieur;
        this.dateMatch = dateMatch;
        this.statut = statut;
        this.vainqueur = vainqueur;
        this.stade = stade;
        this.domicile = domicile;
    }

    // 🧩 Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public LocalDate getDateMatch() {
        return dateMatch;
    }

    public void setDateMatch(LocalDate dateMatch) {
        this.dateMatch = dateMatch;
    }

    public StatutMatch getStatut() {
        return statut;
    }

    public void setStatut(StatutMatch statut) {
        this.statut = statut;
    }

    public VainqueurMatch getVainqueur() {
        return vainqueur;
    }

    public void setVainqueur(VainqueurMatch vainqueur) {
        this.vainqueur = vainqueur;
    }

    public String getStade() {
        return stade;
    }

    public void setStade(String stade) {
        this.stade = stade;
    }

    public boolean isDomicile() {
        return domicile;
    }

    public void setDomicile(boolean domicile) {
        this.domicile = domicile;
    }
}
