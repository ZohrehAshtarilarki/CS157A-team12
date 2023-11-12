package controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import dal.AttendeeDAO;
import model.Attendee;
import java.io.IOException;
import java.util.List;

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
        int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));

        Attendee attendee = new Attendee();
        attendee.setSjsuId(sjsuId);

        attendeeDAO.createAttendee(attendee);

        // Redirect or forward to a success page
        //response.sendRedirect("/views/attendeeHome.jsp");
    }

    private void updateAttendee(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));
        int attendeeId = Integer.parseInt(request.getParameter("attendeeId"));

        // Create Attendee object
        Attendee attendee = new Attendee();
        attendee.setSjsuId(sjsuId);
        attendee.setAttendeeId(attendeeId);

        attendeeDAO.updateAttendee(attendee);

        // Redirect or forward to a success page
        //response.sendRedirect("success.jsp");
    }

    private void deleteAttendee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));

        attendeeDAO.deleteAttendee(sjsuId);

        // Redirect or forward to a success page
        response.sendRedirect("success.jsp");
    }

    private void getAttendeeById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));

        Attendee attendee = attendeeDAO.getAttendeeById(sjsuId);

        // Use the retrieved attendee as needed, e.g., display it on a JSP page
        request.setAttribute("attendee", attendee);
        request.getRequestDispatcher("attendeeDetails.jsp").forward(request, response);
    }

    private void getAllAttendees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Attendee> attendeeList = attendeeDAO.getAllAttendees();

        // Use the retrieved list of attendees as needed, e.g., display it on a JSP page
        request.setAttribute("attendeeList", attendeeList);
        request.getRequestDispatcher("attendeeList.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        // Clean up resources if needed
        attendeeDAO = null;
    }
}
