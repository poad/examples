package com.github.poad.examples.repository;

import com.github.poad.examples.entity.Song;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@Scope("singleton")
public interface SongRepository extends PagingAndSortingRepository<Song, String> {
    Page<Song> findByTitle(String title, Pageable pageable);
    Page<Song> findByArtist(String artist, Pageable pageable);
}
