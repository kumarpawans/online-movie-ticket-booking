package com.psp.test.database;

import com.psp.test.model.Movie;
import com.psp.test.model.Showtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowtimeDatabase {


    private Map<String, Showtime> Showtime = new HashMap<>();

    public ShowtimeDatabase() {
        this.Showtime = new HashMap<>();
    }

    public void addShowtime(Showtime showtime) {
        Showtime.put(showtime.getMovieId(), showtime);
    }

    public Map<String, Showtime> updateShowtime(Showtime movie) {
        Showtime.put(movie.getMovieId(), movie);
        return Showtime;
    }

    public void deleteShowtime(String movieId) {
        Showtime.remove(movieId);
    }

    public Showtime getShowtimeById(String movieId) {
        return Showtime.get(movieId);
    }

    public List<Showtime> getShowtime() {
        return new ArrayList<>(Showtime.values());
    }
}


