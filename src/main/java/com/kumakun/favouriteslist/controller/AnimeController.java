package com.kumakun.favouriteslist.controller;

import com.kumakun.favouriteslist.dto.AnimeDTO;
import com.kumakun.favouriteslist.service.AnimeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

///TODO Introduce Spring Security to authorize request origin
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/favourites/anime")
public class AnimeController {

    private final AnimeService animeService;

    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping
    public Mono<List<AnimeDTO>> getAllFavouriteAnime() {
        return animeService.retrieveAnimeList();
    }

    @PostMapping
    public Mono<List<AnimeDTO>> addListToFavouriteAnime(@RequestBody List<String> animeTitles) {
        return animeService.addAnimeList(animeTitles);
    }
}
