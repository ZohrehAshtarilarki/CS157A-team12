package controller;

import dal.TicketDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;
import model.Ticket;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.TicketService;
import util.TicketUtils;

import java.io.IOException;

@WebServlet(name = "TicketServlet", urlPatterns = { "/TicketServlet" })
public class TicketServlet extends HttpServlet {

    // Handle generation of a ticket
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("generate".equals(action)) {
            int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));
            int eventId = Integer.parseInt(request.getParameter("eventId"));

            // Generate a unique ticket ID or barcode
            String ticketBarcode = TicketUtils.generateUniqueBarcode();

            // Create a ticket object and save it to the database
            Ticket ticket = new Ticket(0, eventId, ticketBarcode, sjsuId);
            TicketDAO ticketDAO = new TicketDAO();
            boolean isCreated = ticketDAO.createTicket(ticket);

            if (isCreated) {
                // Set SJSUID in session for retrieval in dashboard
                HttpSession session = request.getSession();
                session.setAttribute("SJSUID", sjsuId);

                // Redirect to the user's dashboard
                response.sendRedirect("/views/attendeeDash.jsp");
            } else {
                // Handle ticket generation failure
                String errorMessage = "Ticket generation failed.";
                request.setAttribute("message", errorMessage);
                RequestDispatcher dispatcher = request.getRequestDispatcher("views/eventInfo.jsp");
                dispatcher.forward(request, response);
            }
        }
    }

    // Handle downloading of a ticket
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if ("download".equals(action)) {
            String ticketId = request.getParameter("ticketId");
            TicketService ticketService = new TicketService();
            Ticket ticket = ticketService.getTicketByBarcode(ticketId);

            byte[] ticketFile = TicketService.generateTicketFile(ticket);
            // Set the content type, e.g., "application/pdf" for PDF tickets
            response.setContentType("application/pdf");
            response.getOutputStream().write(ticketFile);
        }
    }
}

