package com.kumakun.favouriteslist.service.impl;

import com.kumakun.favouriteslist.client.JikanApiClient;
import com.kumakun.favouriteslist.client.dto.JikanResponseDTO;
import com.kumakun.favouriteslist.dto.AnimeDTO;
import com.kumakun.favouriteslist.model.Anime;
import com.kumakun.favouriteslist.repository.AnimeRepository;
import com.kumakun.favouriteslist.service.AnimeService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

/**
 * Service to retrieve anime data obtained from Jikan.
 * @author kumakun
 * */
@Slf4j
@Service
public class AnimeServiceImpl implements AnimeService {

    private final JikanApiClient jikanApiClient;
    private final AnimeRepository animeRepository;

    public AnimeServiceImpl(JikanApiClient jikanApiClient, AnimeRepository animeRepository) {
        this.jikanApiClient = jikanApiClient;
        this.animeRepository = animeRepository;
    }

    @Override
    public Mono<List<AnimeDTO>> addAnimeList(List<String> animeTitles) {

        return Flux.fromIterable(animeTitles)
                .delayElements(Duration.ofMillis(5000))
                // Handle asynchronous calls to the API
                .flatMap(title -> jikanApiClient.getAnimeData(title)
                        .doOnNext(response -> log.info("Successfully retrieved data for title: {}", title))
                        // Method reference extracts the data list
                        .map(JikanResponseDTO::getData)
                        // Flatten list into individual anime items
                        .flatMapMany(Flux::fromIterable)
                        .map(animeData -> new AnimeDTO(
                                animeData.getTitle(),
                                animeData.getImages().getJpg().getLargeImageUrl(),
                                animeData.getSynopsis(),
                                animeData.getEpisodes(),
                                animeData.getUrl()
                        ))
                        .onErrorResume(error -> {
                            // Log and continue with an empty Flux for this title
                            log.error("Error fetching data for title: {}", title, error);
                            return Flux.empty();
                        })
                )
                // Access constructed AnimeDTO
                .flatMap(animeDTO -> {
                    Anime anime = Anime.builder()
                            .id(UUID.randomUUID())
                            .title(animeDTO.getTitle())
                            .imageUrl(animeDTO.getImageUrl())
                            .synopsis(animeDTO.getSynopsis())
                            .episodes(animeDTO.getEpisodes())
                            .url(animeDTO.getUrl())
                            .build();

                    return animeRepository.save(anime)
                            .doOnSuccess(savedAnime -> log.info("Successfully saved anime: {}", savedAnime.getTitle()))
                            .thenReturn(animeDTO)
                            .onErrorResume(error -> {
                                // Log and continue with an empty Mono for this AnimeDTO
                                log.error("Error saving Anime: {}", animeDTO.getTitle(), error);
                                return Mono.empty();
                            });
                })
                // Collect all the AnimeDTO objects into a Mono object
                .collectList();
    }

    @Override
    public Anime findAnimeByTitle(String title) {
        Anime anime = animeRepository.findByTitle(title).orElseThrow();
        anime.setNewEntry(false);
        return anime;
    }

    @Override
    public Mono<Anime> saveAnime(Anime anime) {
        return animeRepository.save(anime);
    }

    @Override
    public Flux<Anime> fetchAnimeList() {
        return animeRepository.findAll();
    }

    @Override
    public Mono<Void> deleteAnimeByUuid(UUID id) {
        return animeRepository.deleteById(id);
    }
}
