package com.stephanetoukam.stephnews.repositories;

import com.stephanetoukam.stephnews.models.Categorie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategorieRepository extends MongoRepository<Categorie, String> { }
