package com.training.stephnews.repositories;

import com.training.stephnews.models.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Integer> { }
