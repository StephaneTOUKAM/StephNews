package com.stephanetoukam.stephnews.repositories;

import com.stephanetoukam.stephnews.models.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<Article, String> { }
