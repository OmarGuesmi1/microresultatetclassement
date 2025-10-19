package com.esprit.microservice.resultatetclassement.controlleur;

import com.esprit.microservice.resultatetclassement.service.ClassementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resultats")
@CrossOrigin
public class ResultatController {

    private final ResultatService resultService;
    private final ClassementService standingService;

    public ResultatController(ResultatService resultService, ClassementService standingService) {
        this.resultService = resultService;
        this.standingService = standingService;
    }

    // Récupérer tous les résultats
    @GetMapping
    public List<Resultat> getAll() {
        return resultService.getAll();
    }

    // Récupérer tous les résultats d'une compétition
    @GetMapping("/competition/{competitionId}")
    public List<Resultat> getByCompetition(@PathVariable String competitionId) {
        return resultService.getByCompetition(competitionId);
    }

    // Ajouter un résultat et mettre à jour les classements
    @PostMapping
    public Resultat addResult(@RequestBody Resultat r) {
        Resultat saved = resultService.add(r);
        standingService.updateClassements(saved.getIdCompetition());
        return saved;
    }

    // Supprimer un résultat
    @DeleteMapping("/{id}")
    public void deleteResult(@PathVariable String id) {
        resultService.delete(id);
    }
}
