package com.mts.mtswebscraper.controller_advice;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface MovieControllerAdvice {
    ResponseEntity<?> handleIO(IOException e);

    ResponseEntity<?> handle(Exception e);
}
