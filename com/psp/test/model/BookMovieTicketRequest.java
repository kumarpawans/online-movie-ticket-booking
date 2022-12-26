package com.psp.test.model;

import com.psp.test.model.PaymentDetails;
import com.psp.test.model.Seat;
import lombok.Data;

import java.util.List;
@Data
public class BookMovieTicketRequest {
    private String movieId;
    private String theaterId;
    private String showtime;
    private int numTickets;
    private List<Seat> seats;
    private PaymentDetails paymentDetails;

    // constructors, getters, and setters
}
