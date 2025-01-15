package com.kumakun.favouriteslist.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class JikanResponseDTO {
    private List<AnimeData> data;

    @Data
    public static class AnimeData {
        private String title;
        private Images images;
        private String synopsis;
        private int episodes;
        private String url;
    }

    @Data
    public static class Images {
        private Jpg jpg;
    }

    @Data
    public static class Jpg {
        @JsonProperty("large_image_url")
        private String largeImageUrl;
    }
}
