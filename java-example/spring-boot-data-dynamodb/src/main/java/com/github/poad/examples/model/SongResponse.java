package com.github.poad.examples.model;

public class SongResponse {
    private final String title;
    private final String artist;

    public SongResponse(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }
}
