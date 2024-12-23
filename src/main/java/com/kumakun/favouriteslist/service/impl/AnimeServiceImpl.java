package com.kumakun.favouriteslist.service.impl;

import com.kumakun.favouriteslist.client.JikanApiClient;
import com.kumakun.favouriteslist.client.dto.JikanResponseDTO;
import com.kumakun.favouriteslist.dto.AnimeDTO;
import com.kumakun.favouriteslist.model.Anime;
import com.kumakun.favouriteslist.repository.AnimeRepository;
import com.kumakun.favouriteslist.service.AnimeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service to retrieve anime data obtained from Jikan.
 * @author kumakun
 * */
@Service
public class AnimeServiceImpl implements AnimeService {

    /**
     * TODO: Implement AnimeRepository using ReactiveCRUDRepository for complete non-blocking functionality.
     *       True Reactive programming is achieved through this interface and accounts for proper scalability
     *       and performance. The current implementation is an attempt to implement JpaRepository in a
     *       non-blocking way.
     * */

    private final JikanApiClient jikanApiClient;
    private final AnimeRepository animeRepository;

    public AnimeServiceImpl(JikanApiClient jikanApiClient, AnimeRepository animeRepository) {
        this.jikanApiClient = jikanApiClient;
        this.animeRepository = animeRepository;
    }

    /**
     * Retrieve anime saved to database
     * @return Details of anime saved to database
     * */
    @Override
    public Mono<List<AnimeDTO>> retrieveAnimeList() {
                // Executes the blocking call in a separate thread
        return Mono.fromCallable(this::fetchAnimeList)
                // Use a bounded thread pool
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(Flux::fromIterable)
                .map(anime -> new AnimeDTO(
                        anime.getTitle(),
                        anime.getImageUrl(),
                        anime.getSynopsis(),
                        anime.getEpisodes(),
                        anime.getUrl()
                ))
                .collectList();
    }

    /**
     * Make asynchronous calls to Jikan's MyAnimeList API to retrieve details
     * on anime in submitted list and save to database.
     * @param animeTitles - List of anime titles to be added to database
     * @return Details of anime saved to database
     * */
    @Override
    public Mono<List<AnimeDTO>> addAnimeList(List<String> animeTitles) {

        //TODO: Introduce error handling
        return Flux.fromIterable(animeTitles)
                .delayElements(Duration.ofMillis(5000))
                // Handle asynchronous calls to the API
                .flatMap(title -> jikanApiClient.getAnimeData(title)
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
                )
                // Access constructed AnimeDTO
                .flatMap(animeDTO -> {
                    Anime anime = new Anime(
                            UUID.randomUUID(),
                            animeDTO.getTitle(),
                            animeDTO.getImageUrl(),
                            animeDTO.getSynopsis(),
                            animeDTO.getEpisodes(),
                            animeDTO.getUrl()
                    );

                    // Ensures the blocking operation, saveAnime(), is executed
                    // in a non-blocking way within the reactive pipeline
                    return Mono.fromCallable(() -> saveAnime(anime))
                            .subscribeOn(Schedulers.boundedElastic()) // Use separate thread
                            .thenReturn((animeDTO));
                })
                // Collects all the AnimeDTO objects into a Mono object
                .collectList();
    }

    @Override
    public Optional<Anime> findAnimeByTitle(String title) {
        return animeRepository.findByTitle(title);
    }

    @Override
    @Transactional
    public Anime saveAnime(Anime anime) {
        return animeRepository.save(anime);
    }

    @Override
    public List<Anime> fetchAnimeList() {
        return animeRepository.findAll();
    }

    @Override
    public void deleteAnimeByUuid(UUID id) {
        animeRepository.deleteById(id);
    }
}
