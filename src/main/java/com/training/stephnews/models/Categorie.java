package com.training.stephnews.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "categorie")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public String titre;
    public String image;

    @OneToMany(mappedBy = "categorie")
    public List<Article> articles;
}
