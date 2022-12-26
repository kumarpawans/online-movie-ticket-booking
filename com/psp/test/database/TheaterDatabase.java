package com.psp.test.database;

import com.psp.test.model.Theater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TheaterDatabase {
    private Map<String, Theater> theaters = new HashMap<>();

    public TheaterDatabase() {
        this.theaters = new HashMap<>();
    }

    public void addTheater(Theater theater) {
        theaters.put(theater.getId(), theater);
    }

    public Theater updateTheater(Theater theater) {
        theaters.put(theater.getId(), theater);
        return theater;
    }

    public void deleteTheater(String theaterId) {
        theaters.remove(theaterId);
    }

    public Theater getTheaterById(String theaterId) {
        return theaters.get(theaterId);
    }

    public List<Theater> getTheaters() {
        return new ArrayList<>(theaters.values());
    }
}

