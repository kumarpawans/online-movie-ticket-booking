package com.psp.test.service;

import com.psp.test.database.MovieDatabase;
import com.psp.test.database.ShowtimeDatabase;
import com.psp.test.database.TheaterDatabase;
import com.psp.test.model.Movie;
import com.psp.test.model.Showtime;
import com.psp.test.model.Theater;
import org.springframework.stereotype.Service;

@Service
public class CreateShowtimeService {
    private MovieDatabase movieDatabase;
    private TheaterDatabase theaterDatabase;
    private ShowtimeDatabase showtimeDatabase;

    public CreateShowtimeService(MovieDatabase movieDatabase, TheaterDatabase theaterDatabase, ShowtimeDatabase showtimeDatabase) {
        this.movieDatabase = movieDatabase;
        this.theaterDatabase = theaterDatabase;
        this.showtimeDatabase = showtimeDatabase;
    }

    public Showtime createShowtime(Showtime showtime) throws Exception {
        try {
            // retrieve movie and theater details
            Movie movie = movieDatabase.getMovieById(showtime.getMovieId());
            if (movie == null) {
                throw new Exception("Movie not found");
            }
            Theater theater = theaterDatabase.getTheaterById(showtime.getMovieId());
            if (theater == null) {
                throw new Exception("Theater not found");
            }

            // create showtime
            Showtime showtimeObj = new Showtime();
            showtimeObj.setMovieId(showtime.getMovieId());
            showtimeObj.setTheaterId(showtime.getTheaterId());
            showtimeObj.setShowtime(showtime.getShowtime());

            // add showtime to database
            showtimeDatabase.addShowtime(showtimeObj);
            return showtimeObj;
        } catch (Exception e) {
            throw new Exception("Error creating showtime", e);
        }
    }
}

