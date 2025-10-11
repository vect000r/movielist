package com.vect0r.movielist;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movies")
@CrossOrigin(origins="http://localhost:8080")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Movie> findById(@PathVariable Integer id) {
        Movie movie = movieService.getMovieById(id);

        if (movie == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(movie);
    }

    @GetMapping
    public List<Movie> findMovies(@RequestParam(required = false) String title) {
        if (title != null) {
            Movie movie = movieService.getMovieByTitle(title);
            return movie != null ? List.of(movie) : List.of();
        }
        return movieService.getMovies();
    }

    @PostMapping
    public void addMovie(@RequestBody Movie movie) {
        movieService.insertMovie(movie);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Integer id) {
        Movie movie = movieService.getMovieById(id);

        if (movie == null) {
            return ResponseEntity.notFound().build();
        }

        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

}
