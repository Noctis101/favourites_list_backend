package com.kumakun.favouriteslist.repository;

import com.kumakun.favouriteslist.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Service interface for anime list.
 */
@Repository
public interface AnimeRepository extends JpaRepository<Anime, UUID> {
    Optional<Anime> findByTitle(String title);
}
