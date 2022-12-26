package com.psp.test.controller;

import com.psp.test.model.*;
import com.psp.test.model.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/tickets")
public class MovieTicketController {
    @Autowired
    private BookMovieTicketService bookMovieTicketService;
    @Autowired
    private CreateTheaterService createTheaterService;
    @Autowired
    private CreateMovieService createMovieService;
    @Autowired
    private CreateShowtimeService createShowtimeService;

    public MovieTicketController(BookMovieTicketService bookMovieTicketService, CreateTheaterService createTheaterService, CreateMovieService createMovieService, CreateShowtimeService createShowtimeService) {
        this.bookMovieTicketService = bookMovieTicketService;
        this.createTheaterService = createTheaterService;
        this.createMovieService = createMovieService;
        this.createShowtimeService = createShowtimeService;
    }

    @PostMapping("/book")
    public Ticket bookMovieTicket(@RequestHeader("Authorization") String authorization, @RequestBody BookMovieTicketRequest request) throws Exception {
        // validate access token
        User user = null ;//tokenService.verifyAccessToken(authorization);
        if (user == null) {
            throw new Exception("Invalid access token");
        }

        // book ticket
        request.getPaymentDetails().setUser(user);
        return bookMovieTicketService.bookTicket(request.getMovieId(), request.getTheaterId(), request.getShowtime(), request.getNumTickets(), request.getSeats(), request.getPaymentDetails());
    }

    @PostMapping("/theaters/create")
    public Theater createTheater(@RequestHeader("Authorization") String authorization, @RequestBody Theater request) throws Exception {
        return createTheaterService.createTheater(request);
    }

    @PostMapping("/movies/create")
    public Movie createMovie(@RequestHeader("Authorization") String authorization, @RequestBody Movie request) throws Exception {
        return createMovieService.createMovie(request);
    }

    @PostMapping("/showtimes/create")
    public Showtime createShowtime(@RequestHeader("Authorization") String authorization, @RequestBody Showtime request) throws Exception {
        return createShowtimeService.createShowtime(request);
    }
}

