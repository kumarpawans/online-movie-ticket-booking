package com.psp.test.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Showtime {
    private String movieId;
    private String theaterId;
    private Date startTime;
    private Date endTime;
    private List<Seat> seats;
    private String showtime;

}
