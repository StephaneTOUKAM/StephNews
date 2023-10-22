package com.training.stephnews.repositories;

import com.training.stephnews.models.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<Article, String> { }
