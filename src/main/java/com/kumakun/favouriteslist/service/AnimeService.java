package com.kumakun.favouriteslist.service;

import com.kumakun.favouriteslist.dto.AnimeDTO;
import com.kumakun.favouriteslist.model.Anime;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnimeService {
    /**
     * Retrieves list of anime for client.
     * @return list of anime for client
     */
    Mono<List<AnimeDTO>> retrieveAnimeList();

    Mono<List<AnimeDTO>> addAnimeList(List<String> animeTitles);

    /**
     * Retrieves an anime entity.
     * @param title the anime title to search by
     * @return the saved anime
     */
    Optional<Anime> findAnimeByTitle(String title);

    /**
     * Saves an anime entity.
     * @param anime the anime to save
     */
    Anime saveAnime(Anime anime);

    /**
     * Fetches the list of all anime entities.
     * @return a list of anime
     */
    List<Anime> fetchAnimeList();

    /**
     * Deletes an anime entity by its ID.
     * @param id the ID of the anime to delete
     */
    void deleteAnimeByUuid(UUID id);
}