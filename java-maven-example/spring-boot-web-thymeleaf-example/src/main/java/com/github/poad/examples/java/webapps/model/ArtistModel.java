package com.github.poad.examples.java.webapps.model;

import java.util.List;

public class ArtistModel {
    private final String id;

    private final String name;

    private final List<SongModel> songs;

    public ArtistModel() {
        this(null, null, null);
    }

    public ArtistModel(String id, String name, List<SongModel> songs) {
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

    public List<SongModel> getSongs() {
        return songs;
    }
}
