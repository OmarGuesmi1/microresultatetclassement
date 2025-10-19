package com.esprit.microservice.resultatetclassement.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResultatRepository extends MongoRepository<Resultat, String> {

    List<Resultat> findByIdCompetition(String idCompetition);
}

