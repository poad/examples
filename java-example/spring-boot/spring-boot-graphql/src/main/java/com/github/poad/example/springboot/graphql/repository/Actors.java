package com.github.poad.example.springboot.graphql.repository;

import com.github.poad.example.springboot.graphql.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface Actors extends
        JpaRepository<Actor, String> {
}
