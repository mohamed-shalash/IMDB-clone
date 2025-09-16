package com.example.springWorkShop.service;

import com.example.springWorkShop.dao.auth.response.UserResponse;
import com.example.springWorkShop.dto.Movie;
import com.example.springWorkShop.dto.Rating;
import com.example.springWorkShop.dto.User;
import com.example.springWorkShop.repo.MovieRepository;
import com.example.springWorkShop.repo.RatingRepository;
import com.example.springWorkShop.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    public Rating addOrUpdateRating(Long userId, String imdbId, int score) {
        User user = userRepository.findById(userId).orElseThrow();
        Movie movie = movieRepository.findById(imdbId).orElseThrow();

        Rating rating = ratingRepository.findByUserIdAndMovieImdbId(userId, imdbId)
                .orElse(new Rating());

        rating.setUser(user);
        rating.setMovie(movie);
        rating.setScore(score);

        return ratingRepository.save(rating);
    }

    public Integer getUserRating(Long userId, String imdbId) {
        Optional<Rating> rating = ratingRepository.findByUserIdAndMovieImdbId(userId, imdbId);
        return (rating.get() != null) ? rating.get().getScore() : null;
    }

    public double getAverageRating(String imdbId) {
        return ratingRepository.findByMovieImdbId(imdbId).stream()
                .mapToInt(Rating::getScore)
                .average()
                .orElse(0.0);
    }
}
