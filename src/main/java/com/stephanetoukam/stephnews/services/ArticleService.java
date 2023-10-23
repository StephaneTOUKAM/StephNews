package com.stephanetoukam.stephnews.services;

import com.stephanetoukam.stephnews.models.Article;
import com.stephanetoukam.stephnews.repositories.ArticleRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> findAll(){
        var articles = new ArrayList<Article>();
        articleRepository.findAll().forEach(articles::add);
        return articles;
    }

    public Article findOne(@PathVariable("id") String id){
        return articleRepository.findById(id).orElseThrow();
    }

    public void saveArticle(@Valid Article article){
        articleRepository.save(article);
    }

    public void updateArticle(Article article, String id){
        articleRepository.save(article);
    }

    public void deleteArticle(String id){
        articleRepository.deleteById(id);
    }
}
