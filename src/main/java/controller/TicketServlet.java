package controller;

import dal.TicketDAO;
import jakarta.servlet.RequestDispatcher;
import model.Ticket;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.TicketUtils;

import java.io.IOException;

@WebServlet(name = "TicketServlet", urlPatterns = { "/TicketServlet" })
public class TicketServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("generate".equals(action)) {
            String eventID = request.getParameter("EventID");
            String userID = request.getParameter("SJSUID");

            // Generate a unique ticket ID or barcode
            String ticketBarcode = TicketUtils.generateUniqueBarcode();

            // Create a ticket object and save it to the database
            Ticket ticket = new Ticket(null, eventID, ticketBarcode);
            TicketDAO ticketDAO = new TicketDAO();
            boolean isCreated = ticketDAO.createTicket(ticket);

            if (isCreated) {
                // Redirect to the user's dashboard or a page where they can download the ticket
                response.sendRedirect("/views/attendeeDash.jsp?SJSUID=" + userID);
            } else {
                // Handle ticket generation failure
                String errorMessage = "Ticket generation failed.";
                request.setAttribute("message", errorMessage);
                RequestDispatcher dispatcher = request.getRequestDispatcher("views/eventInfo.jsp");
                dispatcher.forward(request, response);
            }
        }
    }
}

