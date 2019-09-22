package com.github.poad.examples.java.webapps.repository;

import com.github.poad.examples.java.webapps.entity.SongEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends RepositoryBase<SongEntity> {
    @Query("SELECT count(s.id) FROM SongEntity s WHERE s.name = :name AND s.artist.id = :id")
    long existsByName(@Param("id") String id, @Param("name") String name);
}
