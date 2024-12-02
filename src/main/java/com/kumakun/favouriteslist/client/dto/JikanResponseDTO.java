package com.kumakun.favouriteslist.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class JikanResponseDTO {
    public List<AnimeData> data;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class AnimeData {
        private String title;
        private Images images;
        private String synopsis;
        private int episodes;
        private String url;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Images {
        private Jpg jpg;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Jpg {
        @JsonProperty("large_image_url")
        private String largeImageUrl;
    }
}
