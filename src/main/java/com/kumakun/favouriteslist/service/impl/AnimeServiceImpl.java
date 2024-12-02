package com.kumakun.favouriteslist.service.impl;

import com.kumakun.favouriteslist.client.JikanApiClient;
import com.kumakun.favouriteslist.dto.AnimeDTO;
import com.kumakun.favouriteslist.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class AnimeServiceImpl implements AnimeService {

    ///TO-DO: Remove autowiring
    @Autowired
    public JikanApiClient jikanApiClient;

    @Override
    public Mono<List<AnimeDTO>> retrieveFavouriteAnime() {
        String animeTitle = "Blue Lock";

        return jikanApiClient.getAnimeData(animeTitle)
                .map(response -> response.getData().stream()
                        .map(animeData -> new AnimeDTO(
                                animeData.getTitle(),
                                animeData.getImages().getJpg().getLargeImageUrl(),
                                animeData.getSynopsis(),
                                animeData.getEpisodes(),
                                animeData.getUrl()
                        ))
                        .toList()
                );
    }
}
