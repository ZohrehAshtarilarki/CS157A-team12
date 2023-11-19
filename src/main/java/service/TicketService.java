package service;

import dal.TicketDAO;
import model.Ticket;

import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
    public List<Ticket> getTicketsByUserID(int userID) {
        return ticketDAO.getTicketsByUserID(userID);
    }

    public static byte[] generateTicketFile(Ticket ticket) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Ticket ID: " + ticket.getTicketId());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Event ID: " + ticket.getEventId());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Barcode: " + ticket.getTicketBarcode());
                contentStream.endText();
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            document.close();

            return byteArrayOutputStream.toByteArray();
        }
    }
}
