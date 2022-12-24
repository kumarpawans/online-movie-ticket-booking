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

    public Ticket bookTicket(String movieId, String theaterId, String showtime, int numTickets, List<Seat> seats, PaymentDetails paymentDetails) throws TicketBookingException {
        // validate input
        if (numTickets < 1 || numTickets > seats.size()) {
            throw new TicketBookingException("Invalid number of tickets");
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
        LocalDateTime showtimeDateTime = LocalDateTime.parse(showtime, formatter);
        if (showtimeDateTime.getHour() >= 12) {
            ticketPrice *= 0.8;
        }
        
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
