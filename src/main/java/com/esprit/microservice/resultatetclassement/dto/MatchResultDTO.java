package com.esprit.microservice.resultatetclassement.dto;

import com.esprit.microservice.resultatetclassement.entity.StatutMatch;
import com.esprit.microservice.resultatetclassement.entity.VainqueurMatch;

public class MatchResultDTO {

    private int butsDomicile;
    private int butsExterieur;
    private StatutMatch statut;
    private VainqueurMatch vainqueur;

    // 🧩 Getters & Setters
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
}