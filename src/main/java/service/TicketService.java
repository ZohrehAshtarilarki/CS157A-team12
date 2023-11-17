package service;

import dal.TicketDAO;
import model.Ticket;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class TicketService {

    private final TicketDAO ticketDAO;

    public TicketService() {
        this.ticketDAO = new TicketDAO();
    }

    // Method to create a new ticket
    public boolean createTicket(Ticket ticket) {
        return ticketDAO.createTicket(ticket);
    }

    // Method to retrieve a ticket by its barcode
    public Ticket getTicketByBarcode(String barcode) {
        return ticketDAO.getTicketByBarcode(barcode);
    }

    // Method to retrieve tickets by user ID
    public List<Ticket> getTicketsByUserID(String userID) {
        return ticketDAO.getTicketsByUserID(userID);
    }

    public static byte[] generateTicketFile(Ticket ticket) {
        // For demonstration, we'll just create a simple string representation of the ticket
        String ticketContent = "Ticket ID: " + ticket.getTicketId() + "\n" +
                "Event ID: " + ticket.getEventId() + "\n" +
                "Barcode: " + ticket.getTicketBarcode();

        // Convert this string to a byte array
        return ticketContent.getBytes(StandardCharsets.UTF_8);
    }
}
