package com.mts.mtswebscraper.service;

import com.mts.mtswebscraper.service.impl.Movie;

import java.io.IOException;
import java.util.List;

public interface MovieService {
    List<Movie> getAllMovies() throws IOException;
}
