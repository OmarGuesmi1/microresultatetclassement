package com.esprit.microservice.resultatetclassement.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clubs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Club {

    @Id
    private String id;
    private String nom;
    private String ville;
    private String pays;
    private String stade;
    private String urlLogo;
}
