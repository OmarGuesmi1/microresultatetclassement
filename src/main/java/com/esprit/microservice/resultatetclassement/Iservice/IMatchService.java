package com.esprit.microservice.resultatetclassement.Iservice;

import com.esprit.microservice.resultatetclassement.dto.MatchResultDTO;
import com.esprit.microservice.resultatetclassement.entity.Match;

import java.util.List;
import java.util.Optional;

public interface IMatchService {

    List<Match> getAll();

    Optional<Match> getById(String id);

    List<Match> getByCompetition(String idCompetition);

    Match add(Match m);

    Match update(String id, Match m);

    Match updateResult(String id, MatchResultDTO dto);

    void delete(String id);
}
