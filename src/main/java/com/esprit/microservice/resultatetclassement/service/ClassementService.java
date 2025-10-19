package com.esprit.microservice.resultatetclassement.service;

import com.esprit.microservice.resultatetclassement.entity.*;
import com.esprit.microservice.resultatetclassement.repository.ClassementRepository;
import com.esprit.microservice.resultatetclassement.repository.ClubRepository;
import com.esprit.microservice.resultatetclassement.repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClassementService {

    private final ClassementRepository classementRepo;
    private final MatchRepository matchRepo; // Use MatchRepository now
    private final ClubRepository clubRepo;

    public ClassementService(ClassementRepository classementRepo,
                             MatchRepository matchRepo,
                             ClubRepository clubRepo) {
        this.classementRepo = classementRepo;
        this.matchRepo = matchRepo;
        this.clubRepo = clubRepo;
    }

    // 🔄 Update standings based on TERMINÉ matches
    public List<Classement> updateClassements(String idCompetition) {
        List<Match> matchesTermines = matchRepo.findByIdCompetition(idCompetition).stream()
                .filter(m -> m.getStatut() == StatutMatch.TERMINÉ)
                .toList();

        Map<String, Classement> classements = initClassements(idCompetition);

        calculerStats(matchesTermines, classements);

        // Delete old standings and save new ones
        classementRepo.deleteAll(classementRepo.findByIdCompetition(idCompetition));
        classementRepo.saveAll(classements.values());

        return trierClassements(classements);
    }

    // 📊 Read-only standings based on TERMINÉ matches
    public List<Classement> getClassement(String idCompetition) {
        List<Match> matchesTermines = matchRepo.findByIdCompetition(idCompetition).stream()
                .filter(m -> m.getStatut() == StatutMatch.TERMINÉ)
                .toList();

        Map<String, Classement> classements = initClassements(idCompetition);

        calculerStats(matchesTermines, classements);

        return trierClassements(classements);
    }

    // Initialize all clubs for the competition
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

    // Calculate club stats from matches
    private void calculerStats(List<Match> matches, Map<String, Classement> classements) {
        for (Match m : matches) {
            Classement domicile = classements.get(m.getIdClubDomicile());
            Classement exterieur = classements.get(m.getIdClubExterieur());

            // Match played
            domicile.setMatchsJoues(domicile.getMatchsJoues() + 1);
            exterieur.setMatchsJoues(exterieur.getMatchsJoues() + 1);

            // Goals
            domicile.setButsPour(domicile.getButsPour() + m.getButsDomicile());
            domicile.setButsContre(domicile.getButsContre() + m.getButsExterieur());
            exterieur.setButsPour(exterieur.getButsPour() + m.getButsExterieur());
            exterieur.setButsContre(exterieur.getButsContre() + m.getButsDomicile());

            // Match result
            if (m.getVainqueur() == VainqueurMatch.DOMICILE) {
                domicile.setVictoires(domicile.getVictoires() + 1);
                exterieur.setDefaites(exterieur.getDefaites() + 1);
            } else if (m.getVainqueur() == VainqueurMatch.EXTERIEUR) {
                exterieur.setVictoires(exterieur.getVictoires() + 1);
                domicile.setDefaites(domicile.getDefaites() + 1);
            } else if (m.getVainqueur() == VainqueurMatch.ÉGALITÉ) {
                domicile.setNuls(domicile.getNuls() + 1);
                exterieur.setNuls(exterieur.getNuls() + 1);
            }
        }

        // Points and goal difference
        classements.values().forEach(c -> {
            c.setDifferenceButs(c.getButsPour() - c.getButsContre());
            c.setPoints(c.getVictoires() * 3 + c.getNuls());
        });
    }

    // Sort clubs by points, goal difference, goals for, and name
    private List<Classement> trierClassements(Map<String, Classement> classements) {
        return classements.values().stream()
                .sorted(Comparator.comparingInt(Classement::getPoints).reversed()
                        .thenComparingInt(Classement::getDifferenceButs).reversed()
                        .thenComparingInt(Classement::getButsPour).reversed()
                        .thenComparing(Classement::getNomClub, Comparator.nullsLast(String::compareTo)))
                .collect(Collectors.toList());
    }
}
