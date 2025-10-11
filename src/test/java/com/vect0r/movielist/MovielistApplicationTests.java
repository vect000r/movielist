package com.vect0r.movielist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovieService movieService;

    @Test
    void findById_ShouldReturn200AndMovie_WhenMovieExists() throws Exception {
        Movie movie = new Movie("Inception", "A mind-bending thriller", "2010", "Christopher Nolan", 9);
        movie.setId(1);

        when(movieService.getMovieById(1)).thenReturn(movie);

        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.director").value("Christopher Nolan"))
                .andExpect(jsonPath("$.year").value("2010"))
                .andExpect(jsonPath("$.rating").value(9));

        verify(movieService, times(1)).getMovieById(1);
    }

    @Test
    void findById_ShouldReturn404_WhenMovieDoesNotExist() throws Exception {
        when(movieService.getMovieById(999)).thenReturn(null);

        mockMvc.perform(get("/movies/999"))
                .andExpect(status().isNotFound());

        verify(movieService, times(1)).getMovieById(999);
    }

    @Test
    void findMovies_ShouldReturn200WithAllMovies_WhenNoTitleProvided() throws Exception {
        Movie movie1 = new Movie("Inception", "A thriller", "2010", "Nolan", 9);
        movie1.setId(1);
        Movie movie2 = new Movie("The Matrix", "Sci-fi action", "1999", "Wachowskis", 10);
        movie2.setId(2);

        List<Movie> movies = Arrays.asList(movie1, movie2);
        when(movieService.getMovies()).thenReturn(movies);

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Inception"))
                .andExpect(jsonPath("$[1].title").value("The Matrix"));

        verify(movieService, times(1)).getMovies();
        verify(movieService, never()).getMovieByTitle(any());
    }

    @Test
    void findMovies_ShouldReturn200WithSingleMovie_WhenTitleProvidedAndFound() throws Exception {
        Movie movie = new Movie("Inception", "A thriller", "2010", "Nolan", 9);
        movie.setId(1);

        when(movieService.getMovieByTitle("Inception")).thenReturn(movie);

        mockMvc.perform(get("/movies").param("title", "Inception"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Inception"));

        verify(movieService, times(1)).getMovieByTitle("Inception");
        verify(movieService, never()).getMovies();
    }

    @Test
    void findMovies_ShouldReturn200WithEmptyArray_WhenTitleProvidedButNotFound() throws Exception {
        when(movieService.getMovieByTitle("NonExistent")).thenReturn(null);

        mockMvc.perform(get("/movies").param("title", "NonExistent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        verify(movieService, times(1)).getMovieByTitle("NonExistent");
    }

    @Test
    void addMovie_ShouldReturn201_WhenValidMovieProvided() throws Exception {
        String movieJson = "{" +
                "\"title\":\"The Shawshank Redemption\"," +
                "\"description\":\"Two imprisoned men bond over years\"," +
                "\"year\":\"1994\"," +
                "\"director\":\"Frank Darabont\"," +
                "\"rating\":10" +
                "}";

        doNothing().when(movieService).insertMovie(any(Movie.class));

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(movieJson))
                .andExpect(status().isCreated());

        verify(movieService, times(1)).insertMovie(any(Movie.class));
    }

    @Test
    void addMovie_ShouldReturn400_WhenInvalidJsonProvided() throws Exception {
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"invalid\""))
                .andExpect(status().isBadRequest());

        verify(movieService, never()).insertMovie(any(Movie.class));
    }

    @Test
    void addMovie_ShouldReturn400_WhenNoBodyProvided() throws Exception {
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        verify(movieService, never()).insertMovie(any(Movie.class));
    }

    @Test
    void deleteMovie_ShouldReturn204_WhenMovieDeleted() throws Exception {
        doNothing().when(movieService).deleteMovie(1);

        mockMvc.perform(delete("/movies/1"))
                .andExpect(status().isNoContent());

        verify(movieService, times(1)).deleteMovie(1);
    }

    @Test
    void deleteMovie_ShouldReturn204_EvenForNonExistentMovie() throws Exception {
        doNothing().when(movieService).deleteMovie(999);

        mockMvc.perform(delete("/movies/999"))
                .andExpect(status().isNoContent());

        verify(movieService, times(1)).deleteMovie(999);
    }

    @Test
    void shouldHaveCorsHeaders_WhenRequestingFromAllowedOrigin() throws Exception {
        Movie movie = new Movie("Test Movie", "Test", "2024", "Test Director", 5);
        movie.setId(1);
        when(movieService.getMovieById(1)).thenReturn(movie);

        mockMvc.perform(get("/movies/1")
                        .header("Origin", "http://localhost:8080"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:8080"));
    }
}