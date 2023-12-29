package com.mts.mtswebscraper.service.impl;

import com.mts.mtswebscraper.service.MovieService;
import com.mts.mtswebscraper.service.Parser;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RedHeadSoundMovieService implements MovieService {
    private final Parser parser;

    @Override
    public List<Movie> getAllMovies() throws IOException {
        List<Movie> movies = new ArrayList<>();
        for (int page = 1; page < 18; page++) {
            Document document = Jsoup.connect("https://redheadsound.studio/page/" + page).get();
            Elements posts = document.getElementsByClass("card d-flex");
            for (var post : posts) {
                movies.add(parser.parseMoviePage(post));
            }
        }

        return movies;
    }
}
