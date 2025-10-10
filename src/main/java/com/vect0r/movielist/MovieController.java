package com.vect0r.movielist;


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
    public Movie findById(@PathVariable Integer id) {
        return movieService.getMovieById(id);
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
    public void deleteMovie(@PathVariable Integer id) { movieService.deleteMovie(id); }

}
