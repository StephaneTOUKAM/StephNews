package com.stephanetoukam.stephnews.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("article")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    private @Id String id;

    @NotBlank(message = "Le titre de l'article ne peut pas être vide")
    private String titre;

    private String resume;

    @NotBlank(message = "Le contenu de l'article ne peut pas être vide")
    private String contenu;

    private String image;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Categorie categorie;
}
