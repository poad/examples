package com.github.poad.example.spring.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Immutable;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table
@Immutable
public class Song {
    @Id
    private final String id;

    @Column
    private final String title;

    @Column(name ="`release`")
    private final Date release;

    @JsonIgnore
    @ManyToOne(optional = false)
    private final Artist artist;

    public Song() {
        this(null, null, null, null);
    }

    public Song(String id, String title, Date release, Artist artist) {
        this.id = id;
        this.title = title;
        this.release = release;
        this.artist = artist;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getRelease() {
        return release;
    }

    public Artist getArtist() {
        return artist;
    }
}
