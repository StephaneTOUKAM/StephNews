package com.training.stephnews.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "article")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public String titre;
    public String resume;
    public String contenu;
    public String image;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    public Categorie categorie;
}
