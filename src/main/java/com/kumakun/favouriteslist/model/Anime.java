package com.kumakun.favouriteslist.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Anime implements Persistable<UUID> {
    @Id
    private UUID id;
    private String title;
    @Column("image_url")
    private String imageUrl;
    private String synopsis;
    private int episodes;
    private String url;

    @Transient
    @Builder.Default
    private boolean isNewEntry = true;

    public boolean isNew() {
        return isNewEntry;
    }
}
