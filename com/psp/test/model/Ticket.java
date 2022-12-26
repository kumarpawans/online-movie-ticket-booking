package com.psp.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    private String id;
    private String movieId;
    private String theaterId;
    private String showtime;
    private List<Seat> seats;
    private String userId;
    private double price;
    private Date purchaseDate;
    private boolean refunded;
}
