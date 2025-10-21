package com.esprit.microservice.resultatetclassement.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDate;

@Document(collection = "matches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    @Id
    private String id;

    private String idCompetition;
    private String idClubDomicile;
    private String idClubExterieur;

    private int butsDomicile;
    private int butsExterieur;
    private LocalDate dateMatch;

    @Field(targetType = FieldType.STRING) 
    private StatutMatch statut;

    @Field(targetType = FieldType.STRING)
    private VainqueurMatch vainqueur;

    private String stade;
    private boolean domicile;

    @Field(targetType = FieldType.STRING)
    private TourMatch tour;
}
