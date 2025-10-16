package com.esprit.microservice.resultatetclassement.controlleur;

import com.esprit.microservice.resultatetclassement.entity.Competition;
import com.esprit.microservice.resultatetclassement.service.CompetitionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/competitions")
@CrossOrigin
public class CompetitionController {

    private final CompetitionService service;

    public CompetitionController(CompetitionService service) {
        this.service = service;
    }

    // Récupérer toutes les compétitions
    @GetMapping
    public List<Competition> getToutes() {
        return service.getAll();
    }

    // Récupérer une compétition par son ID
    @GetMapping("/{id}")
    public Optional<Competition> getParId(@PathVariable String id) {
        return service.getById(id);
    }

    // Ajouter une nouvelle compétition
    @PostMapping
    public Competition ajouter(@RequestBody Competition c) {
        return service.add(c);
    }

    // Mettre à jour une compétition existante
    @PutMapping("/{id}")
    public Competition mettreAJour(@PathVariable String id, @RequestBody Competition c) {
        return service.update(id, c);
    }

    // Supprimer une compétition
    @DeleteMapping("/{id}")
    public void supprimer(@PathVariable String id) {
        service.delete(id);
    }
}
