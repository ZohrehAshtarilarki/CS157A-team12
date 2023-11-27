package controller;

import dal.AttendeeDAO;
import dal.EventOrganizerDAO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import model.Attendee;
import model.EventOrganizer;
import model.User;
import dal.UserDAO;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = { "/UserServlet" })
public class UserServlet extends HttpServlet {
    private UserDAO userDAO;

    /* init() is a lifecycle method that is called by the servlet
    container to initialize the servlet before it starts handling requests
    */
    @Override
    public void init() throws ServletException {
        // calls the init() method of the superclass
        super.init();
        // Initialize an instance of 'UserDAO' and assign it to the 'userDAO' variable
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "updateUser":
                    updateUser(request, response);
                    break;
                case "deleteUser":
                    deleteUser(request, response);
                    break;
                case "registerUser":
                    registerUser(request, response);
                    break;
                case "loginUser":
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
        // Parameter names are used in the jsp files, they should be exactly the same
        int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));
        String sjsuEmail = request.getParameter("sjsuEmail");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String organizationName = request.getParameter("organizationName");

        // Create a User object with constructor parameters
        User user = new User(sjsuId, sjsuEmail, username, password, role);

        // The core Logic of the Registration application is present here. We are going
        // to insert user data in to the database.
        String registeredUser = userDAO.registerUser(user);

        // On success, you can display a message to user on Home page
        if (registeredUser.equals("SUCCESS")) {
            // Ensure that after creating a user, it also triggers the creation of an attendee
            // Attendee creation when the user role is "Attendee"
            if (user.getRole().equals("Attendee")) {
                // Create an Attendee object and set properties
                Attendee attendee = new Attendee(sjsuId, sjsuEmail, username, password, role);

                // Create the attendee using AttendeeDAO
                AttendeeDAO attendeeDAO = new AttendeeDAO();
                attendeeDAO.createAttendee(attendee);
            }

            // Check if user registration is successful
            // Check if the role is EventOrganizer
            if (user.getRole().equals("EventOrganizer")) {
                // Logic to handle EventOrganizer record creation
                // This could involve creating an EventOrganizer object and using EventOrganizerDAO to store it
                EventOrganizer eventOrganizer = new EventOrganizer(sjsuId, sjsuEmail,username,password,role, organizationName);
                eventOrganizer.setOrganizationName(organizationName);

                EventOrganizerDAO eventOrganizerDAO = new EventOrganizerDAO();
                eventOrganizerDAO.createOrganizer(eventOrganizer);
            }
            String successMessage = "Authentication succeed. Please login.";
            request.setAttribute("message", successMessage);
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve login credentials from the request parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userDAO.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                // Creates a new session for a period of interaction between a user and a web application
                HttpSession session = request.getSession();
                session.setAttribute("SJSUID",  user.getSjsuId()); // Store user in session
                // Determine user type and store it in session
                session.setAttribute("Role", user.getRole());

                // User authenticated successfully, redirect to a login success page
                String path = request.getContextPath() + "/views/home.jsp";
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

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get all parameters from the request
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

            // Update the attendee in the database
            boolean updateSuccessful = userDAO.updateUser(user);

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

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));

        userDAO.deleteUser(sjsuId);

        // Redirect or forward to a success page
        response.sendRedirect("/views/attendeeDash.jsp");
    }

    private void getUserById(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));

        User user = userDAO.getUserById(sjsuId);

        // Use the retrieved user as needed, e.g., display it on a JSP page
        request.setAttribute("user", user);
        request.getRequestDispatcher("/views/userDetails.jsp").forward(request, response);
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
