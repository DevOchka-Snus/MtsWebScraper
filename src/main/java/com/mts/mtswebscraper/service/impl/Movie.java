package com.mts.mtswebscraper.service.impl;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class Movie implements Serializable {
    private String name;
    private String releaseYear;
    private int seasonNumber;
    private int loadedSeriesAmount;
    private int allSeriesAmount;
    private String duration;
    private Set<String> country;
    private Set<String> genres;
    private String translateType;
    private String voiceActingStudio;
    private List<String> directors;
    private List<String> actors;
    private String ageRating;
    private String quality;
    private double IMDb;
    private double Kinopoisk;
}
