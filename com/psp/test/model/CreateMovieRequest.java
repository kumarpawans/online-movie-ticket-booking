package com.psp.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CreateMovieRequest implements ICreateMovieRequest {
    private String title;
    private String genre;
    private String plot;
    private String cast;
    private double rating;
    private int duration;
    private String posterUrl;

    // constructors, getters, and setters
}
