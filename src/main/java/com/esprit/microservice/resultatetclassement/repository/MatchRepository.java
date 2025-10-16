package com.esprit.microservice.resultatetclassement.repository;

import com.esprit.microservice.resultatetclassement.entity.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MatchRepository extends MongoRepository<Match, String> {

    List<Match> findByCompetitionId(String idCompetition);
}
