package com.esprit.microservice.resultatetclassement.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clubs")
public class Club {

    @Id
    private String id;
    private String nom;
    private String ville;
    private String pays;
    private String stade;
    private String urlLogo;

    // 🧱 Constructeurs
    public Club() {
    }

    public Club(String id, String nom, String ville, String pays, String stade, String urlLogo) {
        this.id = id;
        this.nom = nom;
        this.ville = ville;
        this.pays = pays;
        this.stade = stade;
        this.urlLogo = urlLogo;
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

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getStade() {
        return stade;
    }

    public void setStade(String stade) {
        this.stade = stade;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }
}
