package com.esprit.microservice.resultatetclassement.service;

import com.esprit.microservice.resultatetclassement.Iservice.IMatchService;
import com.esprit.microservice.resultatetclassement.dto.MatchResultDTO;
import com.esprit.microservice.resultatetclassement.entity.Match;
import com.esprit.microservice.resultatetclassement.repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchService implements IMatchService {

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
        return repo.findByIdCompetition(idCompetition);
    }

    public Match add(Match m) {
        return repo.save(m);
    }

    public Match update(String id, Match m) {
        m.setId(id);
        return repo.save(m);
    }

    public Match updateResult(String id, MatchResultDTO dto) {
        Match match = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + id));

        match.setButsDomicile(dto.getButsDomicile());
        match.setButsExterieur(dto.getButsExterieur());
        match.setStatut(dto.getStatut());
        match.setVainqueur(dto.getVainqueur());

        return repo.save(match);
    }
    public void delete(String id) {
        repo.deleteById(id);
    }
}
