package com.github.poad.example.spring.repository;

import com.github.poad.example.spring.entitiy.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, String> {

    @Query(name = "Artist.uuid", nativeQuery = true)
    String uuid();

    @Query(name = "Artist.findSongByArtistId", nativeQuery = true)
    List<Artist> findSongByArtistId(@Param("id") String id);
}
