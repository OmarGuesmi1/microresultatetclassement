package com.esprit.microservice.resultatetclassement.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "standings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
