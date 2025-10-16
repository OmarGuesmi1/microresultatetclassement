package com.esprit.microservice.resultatetclassement.service;

import com.esprit.microservice.resultatetclassement.entity.Resultat;
import com.esprit.microservice.resultatetclassement.repository.ResultatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultatService {

    private final ResultatRepository repo;

    public ResultatService(ResultatRepository repo) {
        this.repo = repo;
    }

    public List<Resultat> getAll() {
        return repo.findAll();
    }

    public List<Resultat> getByCompetition(String idCompetition) {
        return repo.findByIdCompetition(idCompetition);
    }

    public Resultat add(Resultat r) {
        return repo.save(r);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}