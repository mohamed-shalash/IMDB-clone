package com.example.springWorkShop.service;

import com.example.springWorkShop.dao.auth.response.OmdbMovieDao;
import com.example.springWorkShop.dao.auth.response.OmdbSearchResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Service
public class OmdbService {
//https://www.omdbapi.com/?apikey=220b73d0&s=batman
    @Value("${omdb.api.key}")
    private String apiKey;

    private final String BASE_URL = "https://www.omdbapi.com/";

    public List<OmdbMovieDao> searchByTitle(String title) {
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_URL + "?apikey=" + apiKey + "&s=" + title;

        OmdbSearchResponse response = restTemplate.getForObject(url, OmdbSearchResponse.class);
        return response != null ? response.getSearch() : List.of();
    }

    public OmdbMovieDao searchById(String imdbId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_URL + "?apikey=" + apiKey + "&i=" + imdbId;

        return restTemplate.getForObject(url, OmdbMovieDao.class);
    }


}
