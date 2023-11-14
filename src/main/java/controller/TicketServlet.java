package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "TicketServlet", urlPatterns = { "/TicketServlet" })
public class TicketServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("generate".equals(action)) {
            // Generate ticket logic
        } else if ("scan".equals(action)) {
            // Scan and validate ticket logic
        } else if ("checkIn".equals(action)) {
            // Check-in logic
        }
    }
}
