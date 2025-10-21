package com.esprit.microservice.resultatetclassement.Iservice;

import com.esprit.microservice.resultatetclassement.entity.Classement;
import com.esprit.microservice.resultatetclassement.entity.TypeCompetition;

import java.util.List;

public interface IClassementService {

    List<Classement> updateClassements(String idCompetition, TypeCompetition typeCompetition);

    List<Classement> getClassement(String idCompetition, TypeCompetition typeCompetition);
}
