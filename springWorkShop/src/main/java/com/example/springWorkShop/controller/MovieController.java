package com.example.springWorkShop.controller;

import com.example.springWorkShop.dao.auth.request.MovieAddRequest;
import com.example.springWorkShop.dao.auth.response.MovieResponse;
import com.example.springWorkShop.dao.auth.response.OmdbMovieDao;
import com.example.springWorkShop.dto.Movie;
import com.example.springWorkShop.service.MovieService;
import com.example.springWorkShop.service.OmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:4200")
public class MovieController {

    private final MovieService service;
    private final OmdbService omdbService;

    public MovieController(MovieService service, OmdbService omdbService) {
        this.service = service;
        this.omdbService = omdbService;
    }

    @PostMapping("/add")
    public ResponseEntity<Movie> addMovie(@RequestBody MovieAddRequest movieAddRequest) {
        OmdbMovieDao movie=omdbService.searchById(movieAddRequest.getImdbId());
        return ResponseEntity.ok(service.addMovie(movie));
    }

    @GetMapping("/find/{imdbId}")
    public ResponseEntity<MovieResponse> findMovieByImdbId(@PathVariable String imdbId) {
        return ResponseEntity.ok(service.findMovieByImdbId(imdbId));
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<List<MovieResponse>> searchByTitle(@PathVariable String title) {
        return ResponseEntity.ok(service.searchByName(title));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MovieResponse>> getAllMovies(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getAllMovies(page, size));
    }

    @DeleteMapping("/remove/{imdbId}")
    public ResponseEntity<MessageResponse> removeMovieByImdbId(@PathVariable String imdbId) {
        String message = service.removeMovie(imdbId);
        return ResponseEntity.ok(new MessageResponse(message));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countMovies() {
        return ResponseEntity.ok(service.countMovies());
    }

    public record MessageResponse(String message) {}
}

