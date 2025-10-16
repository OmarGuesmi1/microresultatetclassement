package com.esprit.microservice.resultatetclassement.repository;

import com.esprit.microservice.resultatetclassement.entity.Classement;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClassementRepository extends MongoRepository<Classement, String> {

    List<Classement> findByCompetitionId(String idCompetition);
}
