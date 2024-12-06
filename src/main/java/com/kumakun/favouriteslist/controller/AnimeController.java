package com.kumakun.favouriteslist.controller;

import com.kumakun.favouriteslist.dto.AnimeDTO;
import com.kumakun.favouriteslist.service.AnimeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("api/v1/favourites/anime")
public class AnimeController {

    private final AnimeService animeService;

    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping
    public Mono<List<AnimeDTO>> getAllFavouriteAnime() {
        return animeService.retrieveFavouriteAnime();
    }
}
