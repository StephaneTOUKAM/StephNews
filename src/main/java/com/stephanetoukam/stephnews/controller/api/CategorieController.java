package com.stephanetoukam.stephnews.controller.api;

import com.stephanetoukam.stephnews.models.Categorie;
import com.stephanetoukam.stephnews.services.CategorieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategorieController {

    private final CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping("/")
    public List<Categorie> getAllCategorie(){
        return categorieService.findAll();
    }

    @GetMapping("/details/{id}")
    public Categorie findCategorieById(@PathVariable("id") String id){
        return categorieService.findOne(id);
    }

    @PostMapping("/add")
    public void addCategorie(@RequestBody Categorie categorie){
        categorieService.saveCategorie(categorie);
    }

    @PutMapping("/update/{id}")
    public void editCategorie(@RequestBody Categorie categorie, @PathVariable String id){
        categorieService.updateCategorie(categorie, id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategorie(@PathVariable String id){
        categorieService.deleteCategorie(id);
    }

}
