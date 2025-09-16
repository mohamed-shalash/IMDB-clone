package com.example.springWorkShop.service;

import com.example.springWorkShop.dao.auth.response.MovieResponse;
import com.example.springWorkShop.dao.auth.response.OmdbMovieDao;
import com.example.springWorkShop.dto.Movie;
import com.example.springWorkShop.handeler.error.MovieNotFoundException;
import com.example.springWorkShop.repo.MovieRepository;
import com.example.springWorkShop.repo.RatingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired
    private MovieRepository repo;

    @Autowired
    private RatingRepository ratingRepository;

    public Movie addMovie(OmdbMovieDao movie) {
        Movie newMovie =Movie.builder()
                .imdbId(movie.getImdbID())
                .title(movie.getTitle())
                .year(movie.getYear())
                .poster(movie.getPoster())
                .type(movie.getType())
                .build();
        return repo.save(newMovie);
    }

    @Transactional
    public String removeMovie(String imdbId) {
        if (!repo.existsById(imdbId)) {
            throw new MovieNotFoundException(imdbId);
        }
        ratingRepository.deleteByMovieImdbId(imdbId);
        repo.deleteById(imdbId);
        return "Movie removed successfully";
    }

    public List<MovieResponse> getAllMovies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repo.findAll(pageable)
                .stream()
                .map(movie -> new MovieResponse(movie.getImdbId(), movie.getTitle(), movie.getYear(), movie.getPoster(), movie.getType()))
                .collect(Collectors.toList());
    }

    public MovieResponse findMovieByImdbId(String imdbId) {
        Movie movie = repo.findById(imdbId)
                .orElseThrow(() -> new MovieNotFoundException(imdbId));
        return new MovieResponse(
                movie.getImdbId(),
                movie.getTitle(),
                movie.getYear(),
                movie.getPoster(),
                movie.getType()
        );

    }

    public Long countMovies() {
        return repo.count();
    }

    public List<MovieResponse> searchByName(String title) {
        return repo.findAllByTitleContainingIgnoreCase(title)
                .stream()
                .map(movie -> new MovieResponse(
                        movie.getImdbId(),
                        movie.getTitle(),
                        movie.getYear(),
                        movie.getPoster(),
                        movie.getType()
                ))
                .collect(Collectors.toList());
    }
}

