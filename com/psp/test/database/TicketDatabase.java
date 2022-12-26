package com.psp.test.database;

import com.psp.test.model.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketDatabase {
    private Map<String, Ticket> tickets;

    public TicketDatabase() {
        this.tickets = new HashMap<>();
    }

    public void addTicket(Ticket ticket) {
        tickets.put(ticket.getId(), ticket);
    }

    public Ticket updateTicket(Ticket ticket) {
        tickets.put(ticket.getId(), ticket);
        return ticket;
    }

    public void deleteTicket(String ticketId) {
        tickets.remove(ticketId);
    }

    public Ticket getTicketById(String ticketId) {
        return tickets.get(ticketId);
    }

    public List<Ticket> getTickets() {
        return new ArrayList<>(tickets.values());
    }

    public List<Ticket> getTicketsByTheaterId(String theaterId) {
        return null;
    }
}
