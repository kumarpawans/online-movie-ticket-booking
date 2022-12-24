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
    public Ticket bookMovieTicket(@RequestBody BookMovieTicketRequest request) throws TicketBookingException {
        return bookMovieTicketService.bookTicket(request.getMovieId(), request.getTheaterId(), request.getShowtime(), request.getNumTickets(), request.getSeats(), request.getPaymentDetails());
    }

    @PostMapping("/theaters/create")
    public Theater createTheater(@RequestBody CreateTheaterRequest request) throws TheaterCreationException {
        return createTheaterService.createTheater(request.getName(), request.getLocation(), request.getNumRows(), request.getNumColumns());
    }

    @PostMapping("/movies/create")
    public Movie createMovie(@RequestBody CreateMovieRequest request) throws MovieCreationException {
        return createMovieService.createMovie(request.getTitle(), request.getGenre(), request.getPlot(), request.getCast(), request.getRating(), request.getDuration(), request.getPosterUrl());
    }

    @PostMapping("/showtimes/create")
    public Showtime createShowtime(@RequestBody CreateShowtimeRequest request) throws ShowtimeCreationException {
        return createShowtimeService.createShowtime(request.getMovieId(), request.getTheaterId(), request.getStartTime(), request.getEndTime(), request.getSeats());
    }
}
