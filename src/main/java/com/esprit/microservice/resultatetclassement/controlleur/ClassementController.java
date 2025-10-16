package com.esprit.microservice.resultatetclassement.controlleur;

import com.esprit.microservice.resultatetclassement.entity.Classement;
import com.esprit.microservice.resultatetclassement.service.ClassementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classements")
@CrossOrigin
public class ClassementController {

    private final ClassementService service;

    public ClassementController(ClassementService service) {
        this.service = service;
    }

    // Récupérer les classements d'une compétition
    @GetMapping("/{idCompetition}")
    public List<Classement> getClassements(@PathVariable String idCompetition) {
        return service.getClassements(idCompetition);
    }

    // Mettre à jour les classements d'une compétition
    @PostMapping("/mise-a-jour/{idCompetition}")
    public List<Classement> updateClassements(@PathVariable String idCompetition) {
        return service.updateClassements(idCompetition);
    }
}