public class MovieTicketSystem {
    private MovieDatabase movieDatabase;
    private TheaterDatabase theaterDatabase;
    private TicketDatabase ticketDatabase;

    public MovieTicketSystem(MovieDatabase movieDatabase, TheaterDatabase theaterDatabase, TicketDatabase ticketDatabase) {
        this.movieDatabase = movieDatabase;
        this.theaterDatabase = theaterDatabase;
        this.ticketDatabase = ticketDatabase;
    }

    public List<Movie> searchMovies(String query) {
        // search movie database and return results
    }

    public Movie getMovieDetails(String movieId) {
        // retrieve movie details from database
    }

    public List<Theater> searchTheaters(String location) {
        // search theater database and return results
    }

    public List<Showtime> getShowtimes(String movieId, String theaterId) {
        // retrieve showtimes from database
    }

    public Ticket purchaseTicket(String movieId, String theaterId, String showtime, int numTickets, List<Seat> seats) {
        // process payment and create ticket in database
    }

    public List<Ticket> getTicketHistory(String userId) {
        // retrieve ticket history from database
    }
}
