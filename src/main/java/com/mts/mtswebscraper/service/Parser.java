package com.mts.mtswebscraper.service;

import com.mts.mtswebscraper.service.impl.Movie;
import org.jsoup.nodes.Element;

public interface Parser {
    Movie parseMoviePage(Element element);
}
