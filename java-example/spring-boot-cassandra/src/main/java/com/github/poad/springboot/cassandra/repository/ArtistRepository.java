package com.github.poad.springboot.cassandra.repository;

import com.github.poad.springboot.cassandra.entity.Artist;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends CassandraRepository<Artist, String> {
}
