package com.esprit.microservice.resultatetclassement.service;

import com.esprit.microservice.resultatetclassement.entity.Match;
import com.esprit.microservice.resultatetclassement.repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchService {

    private final MatchRepository repo;

    public MatchService(MatchRepository repo) {
        this.repo = repo;
    }

    public List<Match> getAll() {
        return repo.findAll();
    }

    public Optional<Match> getById(String id) {
        return repo.findById(id);
    }

    public List<Match> getByCompetition(String idCompetition) {
        return repo.findByCompetitionId(idCompetition);
    }

    public Match add(Match m) {
        return repo.save(m);
    }

    public Match update(String id, Match m) {
        m.setId(id);
        return repo.save(m);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}
