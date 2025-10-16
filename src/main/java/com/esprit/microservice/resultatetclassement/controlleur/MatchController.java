package com.esprit.microservice.resultatetclassement.controlleur;

import com.esprit.microservice.resultatetclassement.entity.Match;
import com.esprit.microservice.resultatetclassement.service.MatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/matches")
@CrossOrigin
public class MatchController {

    private final MatchService service;

    public MatchController(MatchService service) {
        this.service = service;
    }

    @GetMapping
    public List<Match> getTous() {
        return service.getAll();
    }

    // 🧩 Récupérer un match par son ID
    @GetMapping("/{id}")
    public Optional<Match> getParId(@PathVariable String id) {
        return service.getById(id);
    }

    // 🧩 Récupérer les matchs par compétition
    @GetMapping("/competition/{competitionId}")
    public List<Match> getParCompetition(@PathVariable String competitionId) {
        return service.getByCompetition(competitionId);
    }

    // 🧩 Ajouter un nouveau match
    @PostMapping
    public Match ajouter(@RequestBody Match m) {
        return service.add(m);
    }

    // 🧩 Mettre à jour un match existant
    @PutMapping("/{id}")
    public Match mettreAJour(@PathVariable String id, @RequestBody Match m) {
        return service.update(id, m);
    }

    // 🧩 Supprimer un match par son ID
    @DeleteMapping("/{id}")
    public void supprimer(@PathVariable String id) {
        service.delete(id);
    }
}
