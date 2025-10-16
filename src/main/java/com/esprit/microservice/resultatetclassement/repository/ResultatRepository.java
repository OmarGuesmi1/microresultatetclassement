package com.esprit.microservice.resultatetclassement.repository;

import com.esprit.microservice.resultatetclassement.entity.Resultat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResultatRepository extends MongoRepository<Resultat, String> {

    List<Resultat> findByCompetitionId(String idCompetition);
}
