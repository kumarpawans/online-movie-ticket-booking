@RestController
@RequestMapping("/tickets")
public class MovieTicketController {
    private BookMovieTicketService bookMovieTicketService;
    private CreateTheaterService createTheaterService;
    private CreateMovieService createMovieService;
    private CreateShowtimeService createShowtimeService;

    public MovieTicketController(BookMovieTicketService bookMovieTicketService, CreateTheaterService createTheaterService, CreateMovieService createMovieService, CreateShowtimeService createShowtimeService) {
        this.bookMovieTicketService = bookMovieTicketService;
        this.createTheaterService = createTheaterService;
        this.createMovieService = createMovieService;
        this.createShowtimeService = createShowtimeService;
    }

    @PostMapping("/book")
    public Ticket bookMovieTicket(@RequestHeader("Authorization") String authorization, @RequestBody BookMovieTicketRequest request) throws TicketBookingException, UnauthorizedException {
        // validate access token
        User user = tokenService.verifyAccessToken(authorization);
        if (user == null) {
            throw new UnauthorizedException("Invalid access token");
        }

        // book ticket
        request.getPaymentDetails().setUserId(user.getId());
        return bookMovieTicketService.bookTicket(request.getMovieId(), request.getTheaterId(), request.getShowtime(), request.getNumTickets(), request.getSeats(), request.getPaymentDetails());
    }

    @PostMapping("/theaters/create")
    public Theater createTheater(@RequestHeader("Authorization") String authorization, @RequestBody CreateTheaterRequest request) throws TheaterCreationException {
        return createTheaterService.createTheater(request.getName(), request.getLocation(), request.getNumRows(), request.getNumColumns());
    }

    @PostMapping("/movies/create")
    public Movie createMovie(@RequestHeader("Authorization") String authorization, @RequestBody CreateMovieRequest request) throws MovieCreationException {
        return createMovieService.createMovie(request.getTitle(), request.getGenre(), request.getPlot(), request.getCast(), request.getRating(), request.getDuration(), request.getPosterUrl());
    }

    @PostMapping("/showtimes/create")
    public Showtime createShowtime(@RequestHeader("Authorization") String authorization, @RequestBody CreateShowtimeRequest request) throws ShowtimeCreationException {
        return createShowtimeService.createShowtime(request.getMovieId(), request.getTheaterId(), request.getStartTime(), request.getEndTime(), request.getSeats());
    }
}

 class CreateMovieRequest {
    private String title;
    private String genre;
    private String plot;
    private String cast;
    private double rating;
    private int duration;
    private String posterUrl;

    // constructors, getters, and setters
}

 class BookMovieTicketRequest {
    private String movieId;
    private String theaterId;
    private String showtime;
    private int numTickets;
    private List<Seat> seats;
    private PaymentDetails paymentDetails;

    // constructors, getters, and setters
}


