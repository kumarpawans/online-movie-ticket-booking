@RestController
@RequestMapping("/tickets")
public class MovieTicketControllerWithCircuitBrakerPattern {
    private BookMovieTicketService bookMovieTicketService;
    private TokenService tokenService;
    private CircuitBreaker circuitBreaker;

    public MovieTicketController(BookMovieTicketService bookMovieTicketService, TokenService tokenService, CircuitBreaker circuitBreaker) {
        this.bookMovieTicketService = bookMovieTicketService;
        this.tokenService = tokenService;
        this.circuitBreaker = circuitBreaker;
    }

    @PostMapping("/book")
    public Ticket bookMovieTicket(@RequestHeader("Authorization") String authorization, @RequestBody BookMovieTicketRequest request) throws TicketBookingException, UnauthorizedException {
        // validate access token
        User user = tokenService.verifyAccessToken(authorization);
        if (user == null) {
            throw new UnauthorizedException("Invalid access token");
        }

        // wrap booking operation in circuit breaker
        return circuitBreaker.execute(() -> {
            // book ticket
            request.getPaymentDetails().setUserId(user.getId());
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
            throw new CircuitBreakerOpenException("Circuit breaker is open");
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

class TokenService {
    private static final String TOKEN_PREFIX = "Bearer ";
    private UserDatabase userDatabase;

    public TokenService(UserDatabase userDatabase) {
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
