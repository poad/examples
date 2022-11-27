package com.github.poad.examples.java.webapps.entity;

import org.springframework.data.annotation.Immutable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "artist")
@Immutable
public class ArtistEntity implements Serializable {
    @Id
    private final String id;

    @Column(name = "name")
    private final String name;

    @OneToMany(mappedBy = "artist", fetch = FetchType.EAGER)
    private final List<SongEntity> songs;

    // constructor for jpa
    public ArtistEntity() {
        this(null, null, null);
    }

    public ArtistEntity(String id, String name, List<SongEntity> songs) {
        this.id = id;
        this.name = name;
        this.songs = songs;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<SongEntity> getSongs() {
        return songs;
    }
}
