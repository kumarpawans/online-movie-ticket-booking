public class BookMovieTicketService {
    private MovieDatabase movieDatabase;
    private TheaterDatabase theaterDatabase;
    private TicketDatabase ticketDatabase;
    private PaymentService paymentService;

    public BookMovieTicketService(MovieDatabase movieDatabase, TheaterDatabase theaterDatabase, TicketDatabase ticketDatabase, PaymentService paymentService) {
        this.movieDatabase = movieDatabase;
        this.theaterDatabase = theaterDatabase;
        this.ticketDatabase = ticketDatabase;
        this.paymentService = paymentService;
    }

    public Ticket bookTicket(String movieId, String theaterId, String showtime, int numTickets, List<Seat> seats, PaymentDetails paymentDetails) throws TicketBookingException {
        // validate input
        if (numTickets < 1 || numTickets > seats.size()) {
            throw new TicketBookingException("Invalid number of tickets");
        }

        // retrieve movie and theater details
        Movie movie = movieDatabase.getMovieById(movieId);
        Theater theater = theaterDatabase.getTheaterById(theaterId);

        // calculate ticket price
        double ticketPrice = movie.getPrice() * numTickets;

        // process payment
        boolean paymentSuccessful = paymentService.processPayment(paymentDetails, ticketPrice);
        if (!paymentSuccessful) {
            throw new TicketBookingException("Payment failed");
        }

        // create ticket
        Ticket ticket = new Ticket(movieId, theaterId, showtime, seats, paymentDetails.getUserId(), ticketPrice, new Date());
        ticketDatabase.addTicket(ticket);

        return ticket;
    }
}
