package com.vect0r.movielist;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> findAll() {
        return movieService.getMovies();
    }

    @GetMapping("{id}")
    public Movie findById(@PathVariable Integer id) {
        return movieService.getMovieById(id);
    }

    @PostMapping
    public void addMovie(@RequestBody Movie movie) {
        movieService.insertMovie(movie);
    }
}
