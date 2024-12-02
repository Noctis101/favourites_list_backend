package com.kumakun.favouriteslist.service;

import com.kumakun.favouriteslist.dto.AnimeDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AnimeService {

    Mono<List<AnimeDTO>> retrieveFavouriteAnime();
}
