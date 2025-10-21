package com.esprit.microservice.resultatetclassement.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDate;

@Document(collection = "competitions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Competition {

    @Id
    private String id;
    private String nom;
    @Field(targetType = FieldType.STRING)
    private TypeCompetition type;
    private String saison;
    private LocalDate dateDebut;
    private LocalDate dateFin;
}
