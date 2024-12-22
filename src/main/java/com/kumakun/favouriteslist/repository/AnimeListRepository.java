package com.kumakun.favouriteslist.repository;

import com.kumakun.favouriteslist.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service interface for Anime entity.
 */
@Repository
public interface AnimeListRepository extends JpaRepository<Anime, UUID> {
    /**
     * Retrieves an anime entity.
     * @param title the anime title to search by
     * @return the saved anime
     */
    Optional<Anime> findAnimeByTitle(String title);

    /**
     * Saves a department entity.
     * @param anime the anime to save
     * @return the saved anime
     */
    Anime saveAnime(Anime anime);

    /**
     * Fetches the list of all anime entities.
     * @return a list of anime
     */
    List<Anime> fetchAnimeList();

    /**
     * Deletes an anime entity by its ID.
     * @param uuid the ID of the anime to delete
     */
    void deleteAnimeByUuid(UUID uuid);
}
