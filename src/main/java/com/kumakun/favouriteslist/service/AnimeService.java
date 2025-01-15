package com.kumakun.favouriteslist.service;

import com.kumakun.favouriteslist.dto.AnimeDTO;
import com.kumakun.favouriteslist.model.Anime;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface AnimeService {
    /**
     * Make asynchronous calls to Jikan's MyAnimeList API to retrieve details
     * on anime in submitted list and save to database.
     * @param animeTitles - List of anime titles to be added to database
     * @return Details of anime saved to database
     * */
    Mono<List<AnimeDTO>> addAnimeList(List<String> animeTitles);

    /**
     * Retrieves an anime entity.
     * @param title the anime title to search by
     * @return the saved anime
     */
    Anime findAnimeByTitle(String title);

    /**
     * Saves an anime entity.
     * @param anime the anime to save
     */
    Mono<Anime> saveAnime(Anime anime);

    /**
     * Fetches the list of all anime entities.
     * @return a list of anime
     */
    Flux<Anime> fetchAnimeList();

    /**
     * Deletes an anime entity by its ID.
     * @param id the ID of the anime to delete
     */
    Mono<Void> deleteAnimeByUuid(UUID id);
}