package com.esprit.microservice.resultatetclassement.controlleur;

import com.esprit.microservice.resultatetclassement.entity.Classement;
import com.esprit.microservice.resultatetclassement.entity.TypeCompetition;
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
    @GetMapping("/{idCompetition}/{typeCompetition}")
    public List<Classement> getClassements(
            @PathVariable String idCompetition,
            @PathVariable TypeCompetition typeCompetition) {
        return service.getClassement(idCompetition, typeCompetition);
    }


    @PostMapping("/mise-a-jour/{idCompetition}/{typeCompetition}")
    public List<Classement> updateClassements(
            @PathVariable String idCompetition,
            @PathVariable TypeCompetition typeCompetition) {
        return service.updateClassements(idCompetition, typeCompetition);
    }


}