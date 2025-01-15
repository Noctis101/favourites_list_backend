package com.kumakun.favouriteslist.repository;

import com.kumakun.favouriteslist.model.Anime;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Service interface for anime list.
 */
@Repository
public interface AnimeRepository extends ReactiveCrudRepository<Anime, UUID> {
    Optional<Anime> findByTitle(String title);
}
