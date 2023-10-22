package com.training.stephnews.repositories;

import com.training.stephnews.models.Categorie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategorieRepository extends MongoRepository<Categorie, String> { }
