package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import dal.AttendeeDAO;
import model.Attendee;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AttendeeServlet", urlPatterns = { "/AttendeeServlet" })
public class AttendeeServlet extends HttpServlet {
    private AttendeeDAO attendeeDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        attendeeDAO = new AttendeeDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "createAttendee":
                    createAttendee(request, response);
                    break;
                case "updateAttendee":
                    updateAttendee(request, response);
                    break;
                case "deleteAttendee":
                    deleteAttendee(request, response);
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
                case "getAttendeeById":
                    getAttendeeById(request, response);
                    break;
                case "getAllAttendees":
                    getAllAttendees(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        }
    }

    private void createAttendee(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get all parameters from the request
        // AttendeeID is auto-generated
        int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));
        String sjsuEmail = request.getParameter("sjsuEmail");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        // Instantiate an Attendee object with all parameters
        Attendee attendee = new Attendee(sjsuId, sjsuEmail, userName, password, role);

        // Use the DAO to create a new Attendee record in the database
        attendeeDAO.createAttendee(attendee);
    }

    private void updateAttendee(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Get all parameters from the request
            int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));
            String sjsuEmail = request.getParameter("sjsuEmail");
            String userName = request.getParameter("userName");
            String password = request.getParameter("password");
            String role = request.getParameter("role");

            // Instantiate an Attendee object with all parameters
            Attendee attendee = new Attendee(sjsuId, sjsuEmail, userName, password, role);

            attendee.setSjsuId(sjsuId);
            attendee.setSjsuEmail(sjsuEmail);
            attendee.setUsername(userName);
            attendee.setPassword(password);
            attendee.setRole(role);

            // Update the attendee in the database
            boolean updateSuccessful = attendeeDAO.updateAttendee(attendee);

            if (updateSuccessful) {
                // Send a success message
                request.getSession().setAttribute("message", "Attendee update successful.");
            } else {
                // Send a failure message if the update was not successful
                request.getSession().setAttribute("message", "Failed to update attendee.");
            }
            // Redirect to the profile page
            response.sendRedirect(request.getContextPath() + "/views/attendeeProfile.jsp");

        } catch (NumberFormatException e) {
            // Handle the NumberFormatException
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format for SJSU ID");
        } catch (Exception e) {
            // Handle other exceptions
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }
    }


    private void deleteAttendee(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));

        try {
            System.out.println("Attempting to delete attendee with SJSUID: " + sjsuId);
            attendeeDAO.deleteAttendee(sjsuId);
            // Set a success message as a request attribute
            request.setAttribute("message", "Attendee successfully deleted.");
            // Forward to the JSP page to display the message
            request.getRequestDispatcher("/views/organizerDash.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // Set an error message as a request attribute
            request.setAttribute("error", "Error while deleting attendee.");
            // Forward to the JSP page to display the error message
            request.getRequestDispatcher("/views/organizerDash.jsp").forward(request, response);
        }
    }


    private void getAttendeeById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));

        Attendee attendee = attendeeDAO.getAttendeeById(sjsuId);

        // Use the retrieved attendee as needed, e.g., display it on a JSP page
        request.setAttribute("attendee", attendee);
        request.getRequestDispatcher("/views/organizerDash.jsp").forward(request, response);
    }

    private void getAllAttendees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Attendee> list = attendeeDAO.getAllAttendees();

        // Use the retrieved list of attendees as needed, e.g., display it on a JSP page
        request.setAttribute("attendeeList", list);
        request.getRequestDispatcher("/views/organizerDash.jsp").forward(request, response);
    }
    @Override
    public void destroy() {
        // Clean up resources if needed
        attendeeDAO = null;
    }
}
