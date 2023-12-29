package com.mts.mtswebscraper.controller;

import org.springframework.http.ResponseEntity;

public interface MovieController {
    ResponseEntity<?> getAllMovies();
}
