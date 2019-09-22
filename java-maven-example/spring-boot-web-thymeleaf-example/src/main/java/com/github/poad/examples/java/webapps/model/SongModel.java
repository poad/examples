package com.github.poad.examples.java.webapps.model;

public class SongModel {
    private final String id;
    private final String name;
    private final String artist;

    public SongModel() {
        this(null, null, null);
    }

    public SongModel(String id, String name, String artist) {
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

    public String getArtist() {
        return artist;
    }
}
