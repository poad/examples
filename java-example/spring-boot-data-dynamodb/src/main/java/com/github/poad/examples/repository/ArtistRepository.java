package com.github.poad.examples.repository;

import com.github.poad.examples.entity.Artist;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@Scope("singleton")
public interface ArtistRepository extends PagingAndSortingRepository<Artist, String> {
    Page<Artist> findByName(String name, Pageable pageable);
}
