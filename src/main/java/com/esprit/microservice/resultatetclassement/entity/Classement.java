package com.esprit.microservice.resultatetclassement.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "standings")
public class Classement {

    @Id
    private String id;

    private String idCompetition;
    private String idClub;
    private String nomClub;

    private int matchsJoues;
    private int victoires;
    private int nuls;
    private int defaites;

    private int butsPour;
    private int butsContre;
    private int differenceButs;
    private int points;

    // 🧱 Constructeur vide
    public Classement() {}

    // 🧱 Constructeur complet
    public Classement(String id, String idCompetition, String idClub, String nomClub,
                      int matchsJoues, int victoires, int nuls, int defaites,
                      int butsPour, int butsContre, int differenceButs, int points) {
        this.id = id;
        this.idCompetition = idCompetition;
        this.idClub = idClub;
        this.nomClub = nomClub;
        this.matchsJoues = matchsJoues;
        this.victoires = victoires;
        this.nuls = nuls;
        this.defaites = defaites;
        this.butsPour = butsPour;
        this.butsContre = butsContre;
        this.differenceButs = differenceButs;
        this.points = points;
    }

    // 🧩 Getters et Setters
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

    public String getIdClub() {
        return idClub;
    }

    public void setIdClub(String idClub) {
        this.idClub = idClub;
    }

    public String getNomClub() {
        return nomClub;
    }

    public void setNomClub(String nomClub) {
        this.nomClub = nomClub;
    }

    public int getMatchsJoues() {
        return matchsJoues;
    }

    public void setMatchsJoues(int matchsJoues) {
        this.matchsJoues = matchsJoues;
    }

    public int getVictoires() {
        return victoires;
    }

    public void setVictoires(int victoires) {
        this.victoires = victoires;
    }

    public int getNuls() {
        return nuls;
    }

    public void setNuls(int nuls) {
        this.nuls = nuls;
    }

    public int getDefaites() {
        return defaites;
    }

    public void setDefaites(int defaites) {
        this.defaites = defaites;
    }

    public int getButsPour() {
        return butsPour;
    }

    public void setButsPour(int butsPour) {
        this.butsPour = butsPour;
    }

    public int getButsContre() {
        return butsContre;
    }

    public void setButsContre(int butsContre) {
        this.butsContre = butsContre;
    }

    public int getDifferenceButs() {
        return differenceButs;
    }

    public void setDifferenceButs(int differenceButs) {
        this.differenceButs = differenceButs;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
