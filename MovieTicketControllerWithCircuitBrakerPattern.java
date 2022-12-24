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
