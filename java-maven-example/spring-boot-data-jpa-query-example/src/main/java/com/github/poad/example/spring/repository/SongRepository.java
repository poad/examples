package com.github.poad.example.spring.repository;

import com.github.poad.example.spring.entitiy.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, String> {
    @Query(name = "Song.uuid", nativeQuery = true)
    String uuid();
}
