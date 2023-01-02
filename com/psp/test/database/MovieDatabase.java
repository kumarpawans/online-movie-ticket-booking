package com.psp.test.database;

import com.psp.test.model.Movie;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MovieDatabase {
    private Map<String, Movie> movies = new HashMap<>();

    public MovieDatabase() {
        this.movies = new HashMap<>();
    }

    public void addMovie(Movie movie) {
        movies.put(movie.getId(), movie);
    }

    public Map<String, Movie> updateTheater(Movie movie) {
        movies.put(movie.getId(), movie);
        return movies;
    }

    public void deleteTheater(String movieId) {
        movies.remove(movieId);
    }

    public Movie getMovieById(String movieId) {
        return movies.get(movieId);
    }

    public List<Movie> getMovies() {
        return new ArrayList<>(movies.values());
    }
}
