package com.github.poad.examples.model;

import java.util.List;

public class SongListResponse {
    private final List<Song> songs;

    public SongListResponse(List<Song> songs) {
        this.songs = songs;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public static class Song {
        private final String title;
        private final String artist;

        public Song(String title, String artist) {
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
}
