package com.example.springWorkShop.controller;

import com.example.springWorkShop.dao.auth.response.OmdbMovieDao;
import com.example.springWorkShop.service.OmdbService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/omdb")
@CrossOrigin(origins = "http://localhost:4200")
public class OmdbController {

    private final OmdbService omdbService;

    public OmdbController(OmdbService omdbService) {
        this.omdbService = omdbService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<OmdbMovieDao>> searchByTitle(@RequestParam String title) {
        return ResponseEntity.ok(omdbService.searchByTitle(title));
    }

    @GetMapping("/movie")
    public ResponseEntity<OmdbMovieDao> searchById(@RequestParam String imdbId) {
        return ResponseEntity.ok(omdbService.searchById(imdbId));
    }
}

