package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import dal.EventOrganizerDAO;
import model.EventOrganizer;
import java.io.IOException;


@WebServlet(name = "EventOrganizerServlet", urlPatterns = { "/EventOrganizerServlet" })
public class EventOrganizerServlet extends HttpServlet {
    private EventOrganizerDAO organizerDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        organizerDAO = new EventOrganizerDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "createOrganizer":
                    createOrganizer(request);
                    break;
                case "updateOrganizer":
                    updateOrganizer(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        }
    }

    private void createOrganizer(HttpServletRequest request) {
        // Get all parameters from the request
        // OrganizerID is auto-generated
        int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));
        String sjsuEmail = request.getParameter("sjsuEmail");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String organizationName = request.getParameter("OrganizationName");

        // Instantiate an EventOrganizer object with all parameters
        EventOrganizer eventOrganizer = new EventOrganizer(sjsuId, sjsuEmail, userName, password, role, organizationName);
        eventOrganizer.setOrganizationName(organizationName);

        organizerDAO.createOrganizer(eventOrganizer);
    }

    private void updateOrganizer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));
            String sjsuEmail = request.getParameter("sjsuEmail");
            String userName = request.getParameter("userName");
            String password = request.getParameter("password");
            String role = request.getParameter("role");
            String organizationName = request.getParameter("organizationName");

            // Instantiate an EventOrganizer object with all parameters
            EventOrganizer eventOrganizer = new EventOrganizer(sjsuId, sjsuEmail, userName, password, role, organizationName);

            eventOrganizer.setSjsuId(sjsuId);
            eventOrganizer.setSjsuEmail(sjsuEmail);
            eventOrganizer.setUsername(userName);
            eventOrganizer.setPassword(password);
            eventOrganizer.setRole(role);
            eventOrganizer.setOrganizationName(organizationName);


            // Update the attendee in the database
            boolean updateSuccessful =  organizerDAO.updateOrganizer(eventOrganizer);

            if (updateSuccessful) {
                // Send a success message
                request.getSession().setAttribute("message", "Organizer update successful.");
            } else {
                // Send a failure message if the update was not successful
                request.getSession().setAttribute("message", "Failed to update organizer.");
            }
            // Redirect to the profile page
            response.sendRedirect(request.getContextPath() + "/views/organizerProfile.jsp");

        } catch (NumberFormatException e) {
            // Handle the NumberFormatException
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format for SJSU ID");
        } catch (Exception e) {
            // Handle other exceptions
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        // Clean up resources if needed
        organizerDAO = null;
    }
}
