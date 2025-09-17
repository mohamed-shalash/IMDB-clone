package com.example.springWorkShop.controller;

import com.example.springWorkShop.dto.Rating;
import com.example.springWorkShop.dto.User;
import com.example.springWorkShop.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/{imdbId}")
    public ResponseEntity<Rating> rateMovie(
            @PathVariable String imdbId,
            @RequestParam int score,
            Authentication authentication) {

        // get userId from logged-in user
        User user = (User) authentication.getPrincipal();
        Rating rating = ratingService.addOrUpdateRating(user.getId(), imdbId, score);
        return ResponseEntity.ok(rating);
    }

    @GetMapping("/{imdbId}/me")
    public ResponseEntity<Integer> getMyRating(
            @PathVariable String imdbId,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Integer score = ratingService.getUserRating(user.getId(), imdbId);
        return ResponseEntity.ok(score != null ? score : 0); // 0 = not rated yet
    }

    @GetMapping("/{imdbId}")
    public ResponseEntity<Double> getAverageRating(@PathVariable String imdbId) {
        return ResponseEntity.ok(ratingService.getAverageRating(imdbId));
    }
}

