package com.esprit.microservice.resultatetclassement.service;

import com.esprit.microservice.resultatetclassement.entity.*;
import com.esprit.microservice.resultatetclassement.repository.ClassementRepository;
import com.esprit.microservice.resultatetclassement.repository.ClubRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClassementService {

    private final ClassementRepository classementRepo;
    private final ResultatRepository resultatRepo;
    private final ClubRepository clubRepo;

    public ClassementService(ClassementRepository classementRepo,
                             ResultatRepository resultatRepo,
                             ClubRepository clubRepo) {
        this.classementRepo = classementRepo;
        this.resultatRepo = resultatRepo;
        this.clubRepo = clubRepo;
    }

    // 🔄 Met à jour les classements dans la base à partir des matchs TERMINÉS
    public List<Classement> updateClassements(String idCompetition) {
        List<Resultat> resultatsTermines = resultatRepo.findByIdCompetition(idCompetition).stream()
                .filter(r -> r.getStatut() == StatutMatch.TERMINÉ)
                .toList();

        Map<String, Classement> classements = initClassements(idCompetition);

        // Calcul des statistiques
        calculerStats(resultatsTermines, classements);

        // Supprimer anciens classements et sauvegarder les nouveaux
        classementRepo.deleteAll(classementRepo.findByIdCompetition(idCompetition));
        classementRepo.saveAll(classements.values());

        return trierClassements(classements);
    }

    // 📊 Lecture seule du classement basé sur les matchs TERMINÉS
    public List<Classement> getClassement(String idCompetition) {
        List<Resultat> resultatsTermines = resultatRepo.findByIdCompetition(idCompetition).stream()
                .filter(r -> r.getStatut() == StatutMatch.TERMINÉ)
                .toList();

        Map<String, Classement> classements = initClassements(idCompetition);

        // Calcul des statistiques
        calculerStats(resultatsTermines, classements);

        return trierClassements(classements);
    }

    // Initialiser tous les clubs pour la compétition
    private Map<String, Classement> initClassements(String idCompetition) {
        Map<String, String> clubIdToNom = clubRepo.findAll().stream()
                .collect(Collectors.toMap(Club::getId, Club::getNom));

        Map<String, Classement> classements = new HashMap<>();
        for (Map.Entry<String, String> entry : clubIdToNom.entrySet()) {
            Classement c = new Classement();
            c.setIdClub(entry.getKey());
            c.setNomClub(entry.getValue());
            c.setMatchsJoues(0);
            c.setVictoires(0);
            c.setNuls(0);
            c.setDefaites(0);
            c.setButsPour(0);
            c.setButsContre(0);
            c.setDifferenceButs(0);
            c.setPoints(0);
            c.setIdCompetition(idCompetition);
            classements.put(entry.getKey(), c);
        }
        return classements;
    }

    // Calculer les stats des clubs à partir des résultats
    private void calculerStats(List<Resultat> resultats, Map<String, Classement> classements) {
        for (Resultat r : resultats) {
            Classement domicile = classements.get(r.getIdClubDomicile());
            Classement exterieur = classements.get(r.getIdClubExterieur());

            // Match joué
            domicile.setMatchsJoues(domicile.getMatchsJoues() + 1);
            exterieur.setMatchsJoues(exterieur.getMatchsJoues() + 1);

            // Buts
            domicile.setButsPour(domicile.getButsPour() + r.getButsDomicile());
            domicile.setButsContre(domicile.getButsContre() + r.getButsExterieur());
            exterieur.setButsPour(exterieur.getButsPour() + r.getButsExterieur());
            exterieur.setButsContre(exterieur.getButsContre() + r.getButsDomicile());

            // Résultat
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

        // Points et différence de buts
        classements.values().forEach(c -> {
            c.setDifferenceButs(c.getButsPour() - c.getButsContre());
            c.setPoints(c.getVictoires() * 3 + c.getNuls());
        });
    }

    // Trier les clubs selon points, différence de buts, buts pour et nom
    private List<Classement> trierClassements(Map<String, Classement> classements) {
        return classements.values().stream()
                .sorted(Comparator.comparingInt(Classement::getPoints).reversed()
                        .thenComparingInt(Classement::getDifferenceButs).reversed()
                        .thenComparingInt(Classement::getButsPour).reversed()
                        .thenComparing(Classement::getNomClub, Comparator.nullsLast(String::compareTo)))
                .collect(Collectors.toList());
    }
}
