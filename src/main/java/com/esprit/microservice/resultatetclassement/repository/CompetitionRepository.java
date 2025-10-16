package com.esprit.microservice.resultatetclassement.repository;

import com.esprit.microservice.resultatetclassement.entity.Competition;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompetitionRepository extends MongoRepository<Competition, String> {}
