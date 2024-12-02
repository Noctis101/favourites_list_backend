package com.kumakun.favouriteslist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AnimeDTO {
    private String title;
    private String imageUrl;
    private String synopsis;
    private int episodes;
    private String url;
}
