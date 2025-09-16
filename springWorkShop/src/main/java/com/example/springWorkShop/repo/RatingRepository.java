package com.example.springWorkShop.repo;

import com.example.springWorkShop.dto.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByMovieImdbId(String imdbId);
    Optional<Rating> findByUserIdAndMovieImdbId(Long userId, String imdbId);

    void deleteByMovieImdbId(String imdbId);
}
