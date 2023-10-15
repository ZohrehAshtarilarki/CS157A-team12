package controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import dal.OrganizerDAO;
import model.Organizer;
import java.io.IOException;
import java.util.List;


public class OrganizerServlet extends HttpServlet {
    private OrganizerDAO organizerDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        organizerDAO = new OrganizerDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "create":
                    createOrganizer(request, response);
                    break;
                case "update":
                    updateOrganizer(request, response);
                    break;
                case "delete":
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
        String organizerId = request.getParameter("organizerId");
        String organizationName = request.getParameter("organizationName");

        Organizer organizer = new Organizer();
        organizer.setSjsuId(sjsuId);
        organizer.setOrganizerId(Integer.parseInt(organizerId));
        organizer.setOrganizationName(organizationName);

        organizerDAO.createOrganizer(organizer);

        // Redirect or forward to a success page
        response.sendRedirect("success.jsp");
    }

    private void updateOrganizer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));
        String organizerId = request.getParameter("organizerId");
        String organizationName = request.getParameter("organizationName");

        Organizer organizer = new Organizer();
        organizer.setSjsuId(sjsuId);
        organizer.setOrganizerId(Integer.parseInt(organizerId));
        organizer.setOrganizationName(organizationName);

        organizerDAO.updateOrganizer(organizer);

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

        Organizer organizer = organizerDAO.getOrganizerById(sjsuId);

        // Use the retrieved organizer as needed, e.g., display it on a JSP page
        request.setAttribute("organizer", organizer);
        request.getRequestDispatcher("organizerDetails.jsp").forward(request, response);
    }

    private void getAllOrganizers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Organizer> organizerList = organizerDAO.getAllOrganizers();

        // Use the retrieved list of organizers as needed, e.g., display it on a JSP page
        request.setAttribute("organizerList", organizerList);
        request.getRequestDispatcher("organizerList.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        // Clean up resources if needed
        organizerDAO = null;
    }
}
