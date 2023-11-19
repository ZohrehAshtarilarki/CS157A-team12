package controller;

import dal.TicketDAO;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Ticket;
import service.TicketService;

import java.io.IOException;

@WebServlet(name = "DownloadTicketServlet", urlPatterns = { "/DownloadTicketServlet" })
public class DownloadTicketServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ticketID = request.getParameter("ticketID");

        // Fetch ticket details from the database
        TicketDAO ticketDAO = new TicketDAO();
        Ticket ticket = ticketDAO.getTicketById(ticketID); // Use the correct method to get a single ticket

        if (ticket != null) {
            // Generate a ticket file (PDF, image, etc.) based on ticket details
            byte[] ticketFile = TicketService.generateTicketFile(ticket);
            // Set response headers for file download
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=ticket-" + ticketID + ".pdf");
            response.setContentLength(ticketFile.length);

            // Write file to response
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(ticketFile);
            outputStream.close();
        } else {
            // Handle case where ticket is not found
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ticket not found");
        }
    }
}
