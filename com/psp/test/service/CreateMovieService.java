package com.psp.test.service;

import com.psp.test.database.MovieDatabase;
import com.psp.test.model.Movie;
import org.springframework.stereotype.Service;

@Service
public class CreateMovieService {
    private MovieDatabase movieDatabase;

    public CreateMovieService(MovieDatabase movieDatabase) {
        this.movieDatabase = movieDatabase;
    }

    public Movie createMovie(Movie movie) throws Exception {
        try {
            // validate movie input
            if (movie.getTitle() == null || movie.getTitle().trim().length() == 0) {
                throw new Exception("Movie title is required");
            }
            if (movie.getTicketPrice() <= 0) {
                throw new Exception("Movie ticket price must be greater than zero");
            }

            // add movie to database
            movieDatabase.addMovie(movie);
            return movie;
        } catch (Exception e) {
            throw new Exception("Error creating movie", e);
        }
    }
}

