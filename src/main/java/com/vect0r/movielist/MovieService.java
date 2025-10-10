package com.vect0r.movielist;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Integer id) {
        return movieRepository.findById(id).orElse(null);
    }

    public Movie getMovieByTitle(String title) {
        return movieRepository.findByTitle(title).orElse(null);
    }

    public void insertMovie(Movie movie) {
        movieRepository.save(movie);
    }

    public void deleteMovie(Integer id) { movieRepository.deleteById(id); }
}
