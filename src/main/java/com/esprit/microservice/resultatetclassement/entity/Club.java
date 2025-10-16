package com.esprit.microservice.resultatetclassement.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clubs")
public class Club {

    @Id
    private String id;
    private String nom;
    private String pays;
    private String urlLogo;

    // 🧱 Constructeurs
    public Club() {
    }

    public Club(String id, String nom, String pays, String urlLogo) {
        this.id = id;
        this.nom = nom;
        this.pays = pays;
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

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }
}
