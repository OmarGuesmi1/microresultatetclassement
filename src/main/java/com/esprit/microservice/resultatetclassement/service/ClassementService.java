package com.esprit.microservice.resultatetclassement.service;

import com.esprit.microservice.resultatetclassement.entity.*;
import com.esprit.microservice.resultatetclassement.repository.ClassementRepository;
import com.esprit.microservice.resultatetclassement.repository.ClubRepository;
import com.esprit.microservice.resultatetclassement.repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClassementService {

    private final ClassementRepository classementRepo;
    private final MatchRepository matchRepo;
    private final ClubRepository clubRepo;

    public ClassementService(ClassementRepository classementRepo,
                             MatchRepository matchRepo,
                             ClubRepository clubRepo) {
        this.classementRepo = classementRepo;
        this.matchRepo = matchRepo;
        this.clubRepo = clubRepo;
    }

    /**
     * Main entry point to update standings or knockout results
     */
    public List<Classement> updateClassements(String idCompetition, TypeCompetition typeCompetition) {

        List<Match> matchesTermines = matchRepo.findByIdCompetition(idCompetition).stream()
                .filter(m -> m.getStatut() == StatutMatch.TERMINÉ)
                .collect(Collectors.toList());

        if (typeCompetition == TypeCompetition.LIGUE || typeCompetition == TypeCompetition.TOURNOI_AMICAL) {
            // League or friendly: compute full standings
            Map<String, Classement> classements = initClassements(matchesTermines, idCompetition);
            calculerStats(matchesTermines, classements);
            classementRepo.deleteAll(classementRepo.findByIdCompetition(idCompetition));
            classementRepo.saveAll(classements.values());
            return trierClassements(classements);
        } else if (typeCompetition == TypeCompetition.LIGUE_DES_CHAMPIONS
                || typeCompetition == TypeCompetition.EUROPA_LEAGUE) {
            // Champions League / Europa League
            // Separate group stage and knockout stage
            Map<TourMatch, List<Match>> matchesByTour = matchesTermines.stream()
                    .filter(m -> m.getTour() != null)
                    .collect(Collectors.groupingBy(Match::getTour, LinkedHashMap::new, Collectors.toList()));

            List<Classement> result = new ArrayList<>();

            // Group stage: compute standings
            if (matchesByTour.containsKey(TourMatch.GROUPES)) {
                List<Match> groupMatches = matchesByTour.get(TourMatch.GROUPES);
                Map<String, Classement> groupClassements = initClassements(groupMatches, idCompetition);
                calculerStats(groupMatches, groupClassements);
                result.addAll(trierClassements(groupClassements));
            }

            // Knockout stages: just store match results per round
            for (TourMatch tour : TourMatch.values()) {
                if (tour != TourMatch.GROUPES && matchesByTour.containsKey(tour)) {
                    for (Match m : matchesByTour.get(tour)) {
                        Classement c = new Classement();
                        c.setIdCompetition(idCompetition);
                        c.setIdClub(m.getIdClubDomicile());
                        c.setNomClub(clubRepo.findById(m.getIdClubDomicile()).map(Club::getNom).orElse("Unknown"));
                        c.setMatchsJoues(1);
                        if (m.getVainqueur() == VainqueurMatch.DOMICILE) c.setVictoires(1);
                        if (m.getVainqueur() == VainqueurMatch.EXTERIEUR) c.setDefaites(1);
                        if (m.getVainqueur() == VainqueurMatch.ÉGALITÉ) c.setNuls(1);
                        result.add(c);
                    }
                }
            }
            classementRepo.deleteAll(classementRepo.findByIdCompetition(idCompetition));
            classementRepo.saveAll(result);
            return result;

        } else if (typeCompetition == TypeCompetition.COUPE_NATIONALE || typeCompetition == TypeCompetition.COUPE_DU_MONDE) {
            // Knockout cup competitions: just store match results per round
            Map<TourMatch, List<Match>> matchesByTour = matchesTermines.stream()
                    .filter(m -> m.getTour() != null)
                    .collect(Collectors.groupingBy(Match::getTour, LinkedHashMap::new, Collectors.toList()));

            List<Classement> result = new ArrayList<>();
            for (TourMatch tour : matchesByTour.keySet()) {
                for (Match m : matchesByTour.get(tour)) {
                    Classement c = new Classement();
                    c.setIdCompetition(idCompetition);
                    c.setIdClub(m.getIdClubDomicile());
                    c.setNomClub(clubRepo.findById(m.getIdClubDomicile()).map(Club::getNom).orElse("Unknown"));
                    c.setMatchsJoues(1);
                    if (m.getVainqueur() == VainqueurMatch.DOMICILE) c.setVictoires(1);
                    if (m.getVainqueur() == VainqueurMatch.EXTERIEUR) c.setDefaites(1);
                    if (m.getVainqueur() == VainqueurMatch.ÉGALITÉ) c.setNuls(1);
                    result.add(c);
                }
            }
            classementRepo.deleteAll(classementRepo.findByIdCompetition(idCompetition));
            classementRepo.saveAll(result);
            return result;
        }

        return Collections.emptyList();
    }

    // Get standings without saving
    public List<Classement> getClassement(String idCompetition, TypeCompetition typeCompetition) {
        return updateClassements(idCompetition, typeCompetition);
    }

    // Initialize only clubs that played in this competition
    private Map<String, Classement> initClassements(List<Match> matches, String idCompetition) {
        Map<String, String> clubIdToNom = new HashMap<>();

        for (Match m : matches) {
            clubIdToNom.putIfAbsent(m.getIdClubDomicile(), clubRepo.findById(m.getIdClubDomicile()).map(Club::getNom).orElse("Unknown"));
            clubIdToNom.putIfAbsent(m.getIdClubExterieur(), clubRepo.findById(m.getIdClubExterieur()).map(Club::getNom).orElse("Unknown"));
        }

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

    // Calculate league/group stats
    private void calculerStats(List<Match> matches, Map<String, Classement> classements) {
        for (Match m : matches) {
            Classement domicile = classements.get(m.getIdClubDomicile());
            Classement exterieur = classements.get(m.getIdClubExterieur());

            if (domicile == null || exterieur == null) continue;

            domicile.setMatchsJoues(domicile.getMatchsJoues() + 1);
            exterieur.setMatchsJoues(exterieur.getMatchsJoues() + 1);

            domicile.setButsPour(domicile.getButsPour() + m.getButsDomicile());
            domicile.setButsContre(domicile.getButsContre() + m.getButsExterieur());
            exterieur.setButsPour(exterieur.getButsPour() + m.getButsExterieur());
            exterieur.setButsContre(exterieur.getButsContre() + m.getButsDomicile());

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

        classements.values().forEach(c -> {
            c.setDifferenceButs(c.getButsPour() - c.getButsContre());
            c.setPoints(c.getVictoires() * 3 + c.getNuls());
        });
    }

    // Sort by points, goal difference, goals for, name
    private List<Classement> trierClassements(Map<String, Classement> classements) {
        return classements.values().stream()
                .sorted(Comparator.comparingInt(Classement::getPoints).reversed()
                        .thenComparingInt(Classement::getDifferenceButs).reversed()
                        .thenComparingInt(Classement::getButsPour).reversed()
                        .thenComparing(Classement::getNomClub, Comparator.nullsLast(String::compareTo)))
                .collect(Collectors.toList());
    }
}
