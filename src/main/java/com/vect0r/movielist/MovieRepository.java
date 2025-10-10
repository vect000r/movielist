package com.vect0r.movielist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie,Integer> {
    Optional<Movie> findByTitle(String title);
    void deleteById(Integer id);
}
