package com.esprit.microservice.resultatetclassement.service;

import com.esprit.microservice.resultatetclassement.entity.Classement;
import com.esprit.microservice.resultatetclassement.entity.Resultat;
import com.esprit.microservice.resultatetclassement.entity.VainqueurMatch;
import com.esprit.microservice.resultatetclassement.repository.ClassementRepository;
import com.esprit.microservice.resultatetclassement.repository.ResultatRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 🏢 Service pour gérer les classements d'une compétition
@Service
public class ClassementService {

    private final ClassementRepository classementRepo;
    private final ResultatRepository resultatRepo;

    public ClassementService(ClassementRepository classementRepo, ResultatRepository resultatRepo) {
        this.classementRepo = classementRepo;
        this.resultatRepo = resultatRepo;
    }

    // Mettre à jour les classements d'une compétition
    public List<Classement> updateClassements(String idCompetition) {
        List<Resultat> resultats = resultatRepo.findByCompetitionId(idCompetition);
        Map<String, Classement> classements = new HashMap<>();

        for (Resultat r : resultats) {
            String idDomicile = r.getIdClubDomicile();
            String idExterieur = r.getIdClubExterieur();

            classements.putIfAbsent(idDomicile, new Classement());
            classements.putIfAbsent(idExterieur, new Classement());

            Classement domicile = classements.get(idDomicile);
            Classement exterieur = classements.get(idExterieur);

            domicile.setIdClub(idDomicile);
            exterieur.setIdClub(idExterieur);

            domicile.setMatchsJoues(domicile.getMatchsJoues() + 1);
            exterieur.setMatchsJoues(exterieur.getMatchsJoues() + 1);

            domicile.setButsPour(domicile.getButsPour() + r.getButsDomicile());
            domicile.setButsContre(domicile.getButsContre() + r.getButsExterieur());
            exterieur.setButsPour(exterieur.getButsPour() + r.getButsExterieur());
            exterieur.setButsContre(exterieur.getButsContre() + r.getButsDomicile());

            if (r.getVainqueur() == VainqueurMatch.DOMICILE) {
                domicile.setVictoires(domicile.getVictoires() + 1);
                exterieur.setDefaites(exterieur.getDefaites() + 1);
            } else if (r.getVainqueur() == VainqueurMatch.EXTERIEUR) {
                exterieur.setVictoires(exterieur.getVictoires() + 1);
                domicile.setDefaites(domicile.getDefaites() + 1);
            } else if (r.getVainqueur() == VainqueurMatch.ÉGALITÉ) {
                domicile.setNuls(domicile.getNuls() + 1);
                exterieur.setNuls(exterieur.getNuls() + 1);
            }
        }

        // Calcul de la différence de buts et des points
        classements.values().forEach(c -> {
            c.setDifferenceButs(c.getButsPour() - c.getButsContre());
            c.setPoints(c.getVictoires() * 3 + c.getNuls());
            c.setIdCompetition(idCompetition);
        });

        // Supprimer les anciens classements et sauvegarder les nouveaux
        classementRepo.deleteAll(classementRepo.findByCompetitionId(idCompetition));
        classementRepo.saveAll(classements.values());

        // Retourner la liste triée
        return classements.values().stream()
                .sorted(Comparator.comparingInt(Classement::getPoints).reversed()
                        .thenComparingInt(Classement::getDifferenceButs).reversed()
                        .thenComparingInt(Classement::getButsPour).reversed())
                .collect(Collectors.toList());
    }

    // Récupérer les classements triés pour une compétition
    public List<Classement> getClassements(String idCompetition) {
        return classementRepo.findByCompetitionId(idCompetition).stream()
                .sorted(Comparator
                        .comparingInt(Classement::getPoints).reversed()
                        .thenComparingInt(Classement::getDifferenceButs).reversed()
                        .thenComparingInt(Classement::getButsPour).reversed()
                        .thenComparing(Classement::getNomClub))
                .collect(Collectors.toList());
    }
}
