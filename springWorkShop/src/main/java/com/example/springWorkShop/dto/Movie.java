package com.example.springWorkShop.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    private String imdbId;

    private String title;
    private String year;
    private String poster;
    private String type;
}

