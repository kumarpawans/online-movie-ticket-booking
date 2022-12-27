package com.psp.test.service;

import com.psp.test.database.MovieDatabase;
import com.psp.test.database.TheaterDatabase;
import com.psp.test.database.TicketDatabase;
import com.psp.test.model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class BookMovieTicketService {
    private MovieDatabase movieDatabase;
    private TheaterDatabase theaterDatabase;
    private TicketDatabase ticketDatabase;
    private PaymentService paymentService;
    
    private static final int AFTERNOON_SHOW_START_HOUR = 12;
    private static final double AFTERNOON_SHOW_DISCOUNT = 0.2;
    private static final double THIRD_TICKET_DISCOUNT = 0.5;


    public BookMovieTicketService(MovieDatabase movieDatabase, TheaterDatabase theaterDatabase, TicketDatabase ticketDatabase, PaymentService paymentService) {
        this.movieDatabase = movieDatabase;
        this.theaterDatabase = theaterDatabase;
        this.ticketDatabase = ticketDatabase;
        this.paymentService = paymentService;
    }

    public Ticket bookTicket(String movieId, String theaterId, String showtime, int numTickets, List<Seat> seats, PaymentDetails paymentDetails) throws Exception {
        // validate input
        if (numTickets < 1 || numTickets > seats.size()) {
            throw new Exception("Invalid number of tickets");
        }

        // retrieve movie and theater details
        Movie movie = movieDatabase.getMovieById(movieId);
        Theater theater = theaterDatabase.getTheaterById(theaterId);
        
        // calculate ticket price
        double ticketPrice = movie.getTicketPrice();
        LocalDateTime showtimeDateTime = LocalDateTime.parse(showtime);
        if (showtimeDateTime.getHour() >= AFTERNOON_SHOW_START_HOUR) {
            ticketPrice *= (1.0 - AFTERNOON_SHOW_DISCOUNT);
        }
        if (numTickets >= 3) {
            ticketPrice *= (1.0 - THIRD_TICKET_DISCOUNT);
        }
        
        double totalPrice = ticketPrice * numTickets;

        // apply discount if showtime is in the afternoon
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime showtimeDateTimeRule = LocalDateTime.parse(showtime, formatter);
        if (showtimeDateTimeRule.getHour() >= 12) {
            ticketPrice *= 0.8;
        }
        paymentDetails.setPaymentAmount(ticketPrice);
        // process payment
        boolean paymentSuccessful = paymentService.processPayment(paymentDetails);
        if (!paymentSuccessful) {
            throw new Exception("Payment failed");
        }

        // create ticket
        Ticket ticket;
        ticket = new Ticket("", movieId, theaterId, showtime, seats, paymentDetails.getUser().getId(), ticketPrice, new Date(), true);
        ticketDatabase.addTicket(ticket);

        return ticket;
    }
}
