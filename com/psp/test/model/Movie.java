package com.psp.test.model;

import lombok.Data;

@Data
public class Movie {
    private String id;
    private String title;
    private String genre;
    private String plot;
    private String cast;
    private double rating;
    private int duration;
    private String posterUrl;
    private double ticketPrice;
    }
