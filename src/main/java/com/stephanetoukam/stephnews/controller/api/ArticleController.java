package com.stephanetoukam.stephnews.controller.api;

import com.stephanetoukam.stephnews.models.Article;
import com.stephanetoukam.stephnews.services.ArticleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<Article> getAllArticle(){
        return articleService.findAll();
    }

    @GetMapping("/details/{id}")
    public Article findArticleById(@PathVariable("id") String id){
        return articleService.findOne(id);
    }

    @PostMapping("/add")
    public void addArticle(@Valid @RequestBody Article article){
        articleService.saveArticle(article);
    }

    @PutMapping("/update/{id}")
    public void editArticle(@RequestBody Article article, @PathVariable String id){
        articleService.updateArticle(article, id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteArticle(@PathVariable String id){
        articleService.deleteArticle(id);
    }

}
