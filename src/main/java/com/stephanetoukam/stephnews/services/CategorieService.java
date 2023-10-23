package com.stephanetoukam.stephnews.services;

import com.stephanetoukam.stephnews.models.Categorie;
import com.stephanetoukam.stephnews.repositories.CategorieRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategorieService {

    private final CategorieRepository categorieRepository;

    public CategorieService(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    public List<Categorie> findAll(){
        List<Categorie> categories = new ArrayList<>();
        categorieRepository.findAll().forEach(categories::add);
        return categories;
    }

    public Categorie findOne(@PathVariable("id") String id){
        return categorieRepository.findById(id).orElseThrow();
    }

    public void saveCategorie(Categorie categorie){
        categorieRepository.save(categorie);
    }

    public void updateCategorie(Categorie categorie, String id){
        categorieRepository.save(categorie);
    }

    public void deleteCategorie(String id){
        categorieRepository.deleteById(id);
    }
}
