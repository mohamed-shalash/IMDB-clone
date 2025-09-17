package com.example.springWorkShop.repo;

import com.example.springWorkShop.dao.auth.response.MovieResponse;
import com.example.springWorkShop.dao.auth.response.OmdbMovieDao;
import com.example.springWorkShop.dto.Movie;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, String> {
    List<Movie> findAllByTitleContainingIgnoreCase(String title);
}

