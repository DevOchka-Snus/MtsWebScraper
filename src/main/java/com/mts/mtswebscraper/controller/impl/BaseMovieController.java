package com.mts.mtswebscraper.controller.impl;

import com.mts.mtswebscraper.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BaseMovieController {
    private final MovieService movieService;

    @SneakyThrows
    @GetMapping
    public ResponseEntity<?> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

}
