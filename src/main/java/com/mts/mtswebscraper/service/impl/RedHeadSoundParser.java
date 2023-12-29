package com.mts.mtswebscraper.service.impl;

import com.mts.mtswebscraper.service.Parser;
import com.mts.mtswebscraper.service.impl.Movie.MovieBuilder;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RedHeadSoundParser implements Parser {
    private Element post;

    @Override
    public Movie parseMoviePage(Element post) {
        this.post = post;
        var movieBuilder = new Movie.MovieBuilder();
        movieBuilder = addName(movieBuilder);
        Element description = post.getElementsByClass("card__desc flex-grow-1").first();
        description = description.getElementsByClass("card__list").first();
        for (var param : description.select("li")) {
            if (!param.text().isBlank()) {
                var typeString = param.text().substring(0, param.text().indexOf(":"));
                movieBuilder = addParam(typeString, param, movieBuilder);
            }
        }
        description = post.getElementsByClass("card__bottom d-flex ai-center").first();
        var ratings = description.getElementsByClass("card__ratings flex-grow-1 d-flex srate").first();
        var ratingText = ratings.getElementsByClass("card__rating-ext imdb").first().text();
        movieBuilder = movieBuilder.IMDb(Double.parseDouble(ratingText));
        ratingText = ratings.getElementsByClass("card__rating-ext kp").first().text();
        movieBuilder = movieBuilder.Kinopoisk(Double.parseDouble(ratingText));

        return movieBuilder.build();
    }

    private MovieBuilder addName(MovieBuilder movieBuilder) {
        Element name = post.getElementsByClass("card__title").first();
        return movieBuilder.name(name.getElementsByTag("a").first().text());
    }


    private MovieBuilder addParam(String typeString, Element param, MovieBuilder movieBuilder) {
        return switch (typeString) {
            case "Актеры" -> addActors(param, movieBuilder);
            case "Жанр" -> addGenres(param, movieBuilder);
            case "Страна" -> addCountry(param, movieBuilder);
            case "Качество" -> addQuality(param, movieBuilder);
            case "Продолжительность" -> addDuration(param, movieBuilder);
            case "Режиссер" -> addDirectors(param, movieBuilder);
            case "Возрастной рейтинг" -> addAgeRating(param, movieBuilder);
            case "Год выпуска" -> addReleaseYear(param, movieBuilder);
            case "Сезон" -> addSeasonNumber(param, movieBuilder);
            case "Серий на сайте" -> addSeriesAmount(param, movieBuilder);
            case "Перевод" -> addTranslateType(param, movieBuilder);
            case "Студия озвучки" -> addStudio(param, movieBuilder);
            default -> throw new RuntimeException();
        };
    }

    private MovieBuilder addStudio(Element param, MovieBuilder movieBuilder) {
        var text = param.text();
        return movieBuilder.voiceActingStudio(text.replaceAll("Студия озвучки: ", ""));
    }

    private MovieBuilder addTranslateType(Element param, MovieBuilder movieBuilder) {
        var text = param.text();
        return movieBuilder.translateType(text.replaceAll("Перевод: ", ""));
    }

    private MovieBuilder addSeriesAmount(Element param, MovieBuilder movieBuilder) {
        var text = param.text();
        var nums = text.replaceAll("Серий на сайте: ", "").split(" из ");
        movieBuilder = movieBuilder.loadedSeriesAmount(Integer.parseInt(nums[0]));
        return movieBuilder.allSeriesAmount(Integer.parseInt(nums[1]));
    }

    private MovieBuilder addSeasonNumber(Element param, MovieBuilder movieBuilder) {
        var text = param.text();
        return movieBuilder.seasonNumber(Integer.parseInt(text.replaceAll("Сезон: ", "")));
    }

    private MovieBuilder addReleaseYear(Element param, MovieBuilder movieBuilder) {
        var text = param.text();
        return movieBuilder.releaseYear(text.replaceAll("Год выпуска: ", ""));
    }

    private MovieBuilder addAgeRating(Element param, MovieBuilder movieBuilder) {
        var text = param.text();
        return movieBuilder.ageRating(text.replaceAll("Возрастной рейтинг: ", ""));
    }

    private MovieBuilder addDirectors(Element param, MovieBuilder movieBuilder) {
        var text = param.text();
        text = text.replaceAll("Режиссер: ", "");
        List<String> directors = Arrays.asList(text.split(", "));
        return movieBuilder.directors(directors);
    }

    private MovieBuilder addDuration(Element param, MovieBuilder movieBuilder) {
        var text = param.text();
        return movieBuilder.duration(text.replaceAll("Продолжительность: ", ""));
    }

    private MovieBuilder addQuality(Element param, MovieBuilder movieBuilder) {
        var text = param.text();
        return movieBuilder.quality(text.replaceAll("Качество: ", ""));
    }

    private MovieBuilder addCountry(Element param, MovieBuilder movieBuilder) {
        var text = param.text();
        text = text.replaceAll("Страна: ", "");
        Set<String> countries = new HashSet<>(Arrays.asList(text.split(", ")));
        return movieBuilder.country(countries);
    }

    private MovieBuilder addGenres(Element param, MovieBuilder movieBuilder) {
        var text = param.text();
        text = text.replaceAll("Жанр: ", "");
        Set<String> genres = new HashSet<>(Arrays.asList(text.split(" / ")));
        return movieBuilder.genres(genres);
    }

    private MovieBuilder addActors(Element param, MovieBuilder movieBuilder) {
        var text = param.text();
        text = text.replaceAll("Актеры: ", "");
        List<String> directors = Arrays.asList(text.split(", "));
        return movieBuilder.actors(directors);
    }
}
