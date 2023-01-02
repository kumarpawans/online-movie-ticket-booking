package com.psp.test.service;

import com.psp.test.database.TheaterCreationException;
import com.psp.test.database.TheaterDatabase;
import com.psp.test.model.Theater;
import org.springframework.stereotype.Service;

@Service
public class CreateTheaterService {
    private TheaterDatabase theaterDatabase;

    public CreateTheaterService(TheaterDatabase theaterDatabase) {
        this.theaterDatabase = theaterDatabase;
    }

    public Theater createTheater(Theater theater) throws TheaterCreationException {
        try {
            // validate theater input
            if (theater.getName() == null || theater.getName().trim().length() == 0) {
                throw new TheaterCreationException("Theater name is required");
            }
            if (theater.getLocation() == null || theater.getLocation().trim().length() == 0) {
                throw new TheaterCreationException("Theater location is required");
            }
            if (theater.getSeat() == null || theater.getSeat().isEmpty()) {
                throw new TheaterCreationException("Theater must have at least one seat");
            }

            // add theater to database
            theaterDatabase.addTheater(theater);
            return theater;
        } catch (Exception e) {
            throw new TheaterCreationException("Error creating theater", e);
        }
    }
}
