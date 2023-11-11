package controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import dal.EventOrganizerDAO;
import model.EventOrganizer;
import java.io.IOException;
import java.util.List;


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
                    createOrganizer(request, response);
                    break;
                case "updateOrganizer":
                    updateOrganizer(request, response);
                    break;
                case "deleteOrganizer":
                    deleteOrganizer(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "getOrganizerById":
                    getOrganizerById(request, response);
                    break;
                case "getAllOrganizers":
                    getAllOrganizers(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        }
    }

    private void createOrganizer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));
        int organizerId = Integer.parseInt(request.getParameter("organizerId"));
        String organizationName = request.getParameter("organizationName");

        EventOrganizer eventOrganizer = new EventOrganizer();
        eventOrganizer.setSjsuId(sjsuId);
        eventOrganizer.setOrganizerId(organizerId);
        eventOrganizer.setOrganizationName(organizationName);

        organizerDAO.createOrganizer(eventOrganizer);

        // Redirect or forward to a success page
        response.sendRedirect("success.jsp");
    }

    private void updateOrganizer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));
    	int organizerId = Integer.parseInt(request.getParameter("organizerId"));
        String organizationName = request.getParameter("organizationName");

        EventOrganizer eventOrganizer = new EventOrganizer();
        eventOrganizer.setSjsuId(sjsuId);
        eventOrganizer.setOrganizerId(organizerId);
        eventOrganizer.setOrganizationName(organizationName);

        organizerDAO.updateOrganizer(eventOrganizer);

        // Redirect or forward to a success page
        response.sendRedirect("success.jsp");
    }

    private void deleteOrganizer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));

        organizerDAO.deleteOrganizer(sjsuId);

        // Redirect or forward to a success page
        response.sendRedirect("success.jsp");
    }

    private void getOrganizerById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));

        EventOrganizer eventOrganizer = organizerDAO.getOrganizerById(sjsuId);

        // Use the retrieved organizer as needed, e.g., display it on a JSP page
        request.setAttribute("organizer", eventOrganizer);
        request.getRequestDispatcher("organizerDetails.jsp").forward(request, response);
    }

    private void getAllOrganizers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<EventOrganizer> eventOrganizerList = organizerDAO.getAllOrganizers();

        // Use the retrieved list of organizers as needed, e.g., display it on a JSP page
        request.setAttribute("organizerList", eventOrganizerList);
        request.getRequestDispatcher("organizerList.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        // Clean up resources if needed
        organizerDAO = null;
    }
}
