package com.example.springWorkShop.dao.auth.response;


import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponse {
    private String imdbId;

    private String title;
    private String year;
    private String poster;
    private String type;
}
