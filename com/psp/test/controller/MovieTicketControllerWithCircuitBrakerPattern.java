package com.psp.test.controller;

import com.psp.test.model.BookMovieTicketRequest;
import com.psp.test.model.User;
import com.psp.test.service.BookMovieTicketService;
import com.psp.test.model.Ticket;
import org.apache.catalina.UserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Callable;

@RestController
@RequestMapping("/tickets")
public class MovieTicketControllerWithCircuitBrakerPattern {
    private BookMovieTicketService bookMovieTicketService;
    private TokenService tokenService;
    private CircuitBreaker circuitBreaker;

    public MovieTicketControllerWithCircuitBrakerPattern(BookMovieTicketService bookMovieTicketService, TokenService tokenService, CircuitBreaker circuitBreaker) {
        this.bookMovieTicketService = bookMovieTicketService;
        this.tokenService = tokenService;
        this.circuitBreaker = circuitBreaker;
    }

    @PostMapping("/book")
    public Ticket bookMovieTicket(@RequestHeader("Authorization") String authorization, @RequestBody BookMovieTicketRequest request) throws Exception {
        // validate access token
        User user = tokenService.verifyAccessToken(authorization);
        if (user == null) {
            throw new Exception("Invalid access token");
        }

        // wrap booking operation in circuit breaker
        return circuitBreaker.execute(() -> {
            // book ticket
            request.getPaymentDetails().setUser(user);
            return bookMovieTicketService.bookTicket(request.getMovieId(), request.getTheaterId(), request.getShowtime(), request.getNumTickets(), request.getSeats(), request.getPaymentDetails());
        });
    }
}

class CircuitBreaker {
    private static final int DEFAULT_THRESHOLD = 5;
    private static final long DEFAULT_TIMEOUT = 1000L;
    private int threshold;
    private long timeout;
    private int failureCount;
    private long lastFailure;

    public CircuitBreaker() {
        this(DEFAULT_THRESHOLD, DEFAULT_TIMEOUT);
    }

    public CircuitBreaker(int threshold, long timeout) {
        this.threshold = threshold;
        this.timeout = timeout;
    }

    public <T> T execute(Callable<T> operation) throws Exception {
        // check if circuit is open
        if (failureCount >= threshold && System.currentTimeMillis() - lastFailure < timeout) {
            throw new Exception("Circuit breaker is open");
        }

        try {
            // execute operation
            return operation.call();
        } catch (Exception e) {
            // increment failure count
            failureCount++;
            lastFailure = System.currentTimeMillis();

            // throw original exception
            throw e;
        }
    }
}
@Component
class TokenService {
    private static final String TOKEN_PREFIX = "Bearer ";
    @Autowired
    private com.psp.test.database.UserDatabase userDatabase;

    public TokenService(com.psp.test.database.UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public User verifyAccessToken(String authorization) {
        // extract token from authorization header
        if (authorization == null || !authorization.startsWith(TOKEN_PREFIX)) {
            return null;
        }
        String token = authorization.substring(TOKEN_PREFIX.length());

        // verify token and return user
        return userDatabase.getUserByToken(token);
    }
}
