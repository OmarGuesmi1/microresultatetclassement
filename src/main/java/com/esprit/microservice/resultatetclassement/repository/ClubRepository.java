package com.esprit.microservice.resultatetclassement.repository;

import com.esprit.microservice.resultatetclassement.entity.Club;
import org.springframework.data.mongodb.repository.MongoRepository;

// 🏛️ Repository pour gérer les opérations CRUD sur les clubs
public interface ClubRepository extends MongoRepository<Club, String> {}
