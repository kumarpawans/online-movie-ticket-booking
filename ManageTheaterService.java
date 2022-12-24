public class ManageTheaterService {
    private TheaterDatabase theaterDatabase;
    private TicketDatabase ticketDatabase;

    public ManageTheaterService(TheaterDatabase theaterDatabase, TicketDatabase ticketDatabase) {
        this.theaterDatabase = theaterDatabase;
        this.ticketDatabase = ticketDatabase;
    }


    public Theater createTheater(String name, String location, int numRows, int numColumns) throws TheaterCreationException {
        // validate input
        if (numRows < 1 || numColumns < 1) {
            throw new TheaterCreationException("Invalid number of rows or columns");
        }

        // create theater
        Theater theater = new Theater(name, location, numRows, numColumns);
        theaterDatabase.addTheater(theater);

        return theater;
    }


    public Theater updateTheater(String theaterId, String name, String location, int numRows, int numColumns) throws TheaterNotFoundException, TheaterUpdateException {
        // retrieve theater
        Theater theater = theaterDatabase.getTheaterById(theaterId);
        if (theater == null) {
            throw new TheaterNotFoundException("Theater not found");
        }

        // validate input
        if (numRows < 1 || numColumns < 1) {
            throw new TheaterUpdateException("Invalid number of rows or columns");
        }

        // update theater
        theater.setName(name);
        theater.setLocation(location);
        theater.setNumRows(numRows);
        theater.setNumColumns(numColumns);
        theaterDatabase.updateTheater(theater);

        return theater;
    }

    public void deleteTheater(String theaterId) throws TheaterNotFoundException, TheaterDeleteException {
        // retrieve theater
        Theater theater = theaterDatabase.getTheaterById(theaterId);
        if (theater == null) {
            throw new TheaterNotFoundException("Theater not found");
        }

        // check if theater has any tickets
        List<Ticket> tickets = ticketDatabase.getTicketsByTheaterId(theaterId);
        if (!tickets.isEmpty()) {
            throw new TheaterDeleteException("Theater has tickets and cannot be deleted");
        }

        // delete theater
        theaterDatabase.deleteTheater(theaterId);
    }
}
