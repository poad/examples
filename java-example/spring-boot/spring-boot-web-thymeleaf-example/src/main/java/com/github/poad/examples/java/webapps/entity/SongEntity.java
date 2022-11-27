package com.github.poad.examples.java.webapps.entity;

import org.springframework.data.annotation.Immutable;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "song")
@Immutable
public class SongEntity implements Serializable {
    @Id
    private final String id;

    @Column(name = "name")
    private final String name;

    @ManyToOne(optional = false)
    private final ArtistEntity artist;

    public SongEntity() {
        this(null, null, null);
    }

    public SongEntity(String id, String name, ArtistEntity artist) {
        this.id = id;
        this.name = name;
        this.artist = artist;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArtistEntity getArtist() {
        return artist;
    }
}
