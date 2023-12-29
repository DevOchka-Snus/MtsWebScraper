package com.mts.mtswebscraper.controller_advice.impl;

import com.mts.mtswebscraper.controller_advice.MovieControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class BaseMovieControllerAdvice implements MovieControllerAdvice {
    @ExceptionHandler(IOException.class)
    @Override
    public ResponseEntity<?> handleIO(IOException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @Override
    public ResponseEntity<?> handle(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
