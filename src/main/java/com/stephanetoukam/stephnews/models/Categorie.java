package com.stephanetoukam.stephnews.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("categorie")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categorie {

    private @Id String id;

    @NotBlank(message = "Le titre de la categorie ne peut pas être vide")
    private String titre;

    private String image;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Article> articles;
}
