package com.github.poad.example.spring.entitiy;

import org.springframework.data.annotation.Immutable;

import jakarta.persistence.*;

import java.util.*;
import java.sql.Date;

@Entity
@Table
@Immutable
public class Artist {
    @Id
    private final String id;

    @Column
    private final String name;

    @Column
    private final String nickName;

    @Column
    private final Date birthday;

    @OneToMany(mappedBy = "artist", fetch = FetchType.EAGER)
    private final List<Song> songs;

    public Artist() {
        this(null, null, null, null, null);
    }

    public Artist(String id, String name, String nickName, Date birthday, List<Song> songs) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.birthday = birthday;
        this.songs = songs;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public List<Song> getSongs() {
        return songs;
    }
}
