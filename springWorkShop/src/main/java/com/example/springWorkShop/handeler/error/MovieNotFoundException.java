package com.example.springWorkShop.handeler.error;


public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(String imdbId) {
        super("Movie with ID " + imdbId + " not found");
    }
}
