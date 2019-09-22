package com.github.poad.examples.java.webapps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface RepositoryBase<T> extends JpaRepository<T, String> {
    @Query(value = "SELECT UUID();", nativeQuery = true)
    String uuid();
}
