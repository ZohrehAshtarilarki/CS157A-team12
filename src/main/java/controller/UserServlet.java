package controller;

import dal.AttendeeDAO;
import dal.EventOrganizerDAO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import model.Attendee;
import model.EventOrganizer;
import model.User;
import dal.UserDAO;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = { "/UserServlet" })
public class UserServlet extends HttpServlet {
    private UserDAO userDAO;
    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "update":
                    updateUser(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                case "register":
                    registerUser(request, response);
                    break;
                case "login":
                    loginUser(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "getUserById":
                    getUserById(request, response);
                    break;
                case "getUserByUsername":
                    getUserByUsername(request, response);
                    break;
                case "getAllUsers":
                    getAllUsers(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        }
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Copying all the input parameters in to local variables
        int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));
        String sjsuEmail = request.getParameter("sjsuEmail");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        // Create user object
        User user = new User();
        user.setSjsuId(sjsuId);
        user.setSjsuEmail(sjsuEmail);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        // The core Logic of the Registration application is present here. We are going
        // to insert user data in to the database.
        String registeredUser = userDAO.registerUser(user);

        // On success, you can display a message to user on Home page
        if (registeredUser.equals("SUCCESS")) {
            // Ensure that after creating a user, it also triggers the creation of an attendee
            // Attendee creation when the user role is "Attendee"
            if (user.getRole().equals("Attendee")) {
                // Create an Attendee object and set properties
                Attendee attendee = new Attendee();
                attendee.setSjsuId(user.getSjsuId());

                // Create the attendee using AttendeeDAO
                AttendeeDAO attendeeDAO = new AttendeeDAO();
                attendeeDAO.createAttendee(attendee);
            }

            // Check if user registration is successful
            // Check if the role is EventOrganizer
            if (user.getRole().equals("EventOrganizer")) {
                String organizationName = request.getParameter("organizationName");
                // Logic to handle EventOrganizer record creation
                // This could involve creating an EventOrganizer object and using EventOrganizerDAO to store it
                EventOrganizer eventOrganizer = new EventOrganizer();
                eventOrganizer.setSjsuId(user.getSjsuId());
                eventOrganizer.setOrganizationName(organizationName);

                EventOrganizerDAO eventOrganizerDAO = new EventOrganizerDAO();
                eventOrganizerDAO.createOrganizer(eventOrganizer);
            }
            String successMessage = "Authentication succeed. Please login.";
            request.setAttribute("message", successMessage);
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
        // Redirect or forward to a success page
        //response.sendRedirect("/views/attendeeHome.jsp");
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve login credentials from the request parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("Username: " + username + ", password: "+password);

        try {
            User user = userDAO.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                String path = getString(request, user);
                System.out.println("User role: " + user.getRole());
                System.out.println("Redirecting to: " + path);

                // Determine the redirect path based on the user's role
                response.sendRedirect(path);
            } else {
                // Authentication failed, set an error message and forward to the login page
                String errorMessage = "Authentication failed. Please check your username and password.";
                request.setAttribute("message", errorMessage);
                RequestDispatcher dispatcher = request.getRequestDispatcher("views/login.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            // Handle other unexpected exceptions
            e.printStackTrace(); // Log the exception for debugging
            request.setAttribute("message", "An unexpected error occurred. Please try again later.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/login.jsp");
            dispatcher.forward(request, response);
        }
    }

    private String getString(HttpServletRequest request, User user) {
        String path = request.getContextPath();
        // Check if the role is null
        if (user.getRole() != null) {
            switch (user.getRole()) {
                case "Attendee":
                    path += "/views/attendeeHome.jsp";
                    break;
                case "EventOrganizer":
                    path += "/views/eventOrganizerHome.jsp";
                    break;
                default:
                    path += "/views/defaultHome.jsp"; // Default home page if role is unknown
                    break;
            }
        }
        return path;
    }


    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));
        String sjsuEmail = request.getParameter("sjsuEmail");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        User user = new User();
        user.setSjsuId(sjsuId);
        user.setSjsuEmail(sjsuEmail);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        userDAO.updateUser(user);

        // Redirect or forward to a success page
        response.sendRedirect("/views/attendeeHome.jsp");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));

        userDAO.deleteUser(sjsuId);

        // Redirect or forward to a success page
        response.sendRedirect("/views/attendeeHome.jsp");
    }

    private void getUserById(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));

        User user = userDAO.getUserById(sjsuId);

        // Use the retrieved user as needed, e.g., display it on a JSP page
        request.setAttribute("user", user);
        request.getRequestDispatcher("userDetails.jsp").forward(request, response);
    }

    private void getUserByUsername(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");

        User user = userDAO.getUserByUsername(username);

        // Use the retrieved user as needed, e.g., display it on a JSP page
        request.setAttribute("user", user);
        request.getRequestDispatcher("userDetails.jsp").forward(request, response);
    }

    private void getAllUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> userList = userDAO.getAllUsers();

        // Use the retrieved list of users as needed, e.g., display it on a JSP page
        request.setAttribute("userList", userList);
        request.getRequestDispatcher("userList.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        // Clean up resources if needed
        userDAO = null;
    }
}
