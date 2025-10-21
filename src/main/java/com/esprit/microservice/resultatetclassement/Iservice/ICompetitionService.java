package com.esprit.microservice.resultatetclassement.Iservice;

import com.esprit.microservice.resultatetclassement.entity.Competition;

import java.util.List;
import java.util.Optional;

public interface ICompetitionService {

    List<Competition> getAll();

    Optional<Competition> getById(String id);

    Competition add(Competition c);

    Competition update(String id, Competition c);

    void delete(String id);
}
