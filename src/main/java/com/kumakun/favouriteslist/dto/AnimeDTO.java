package com.kumakun.favouriteslist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AnimeDTO {
    private String title;
    private String imageUrl;
    private String synopsis;
    private int episodes;
    private String url;
}
