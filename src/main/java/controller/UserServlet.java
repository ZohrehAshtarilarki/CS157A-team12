package controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import model.User;
import dal.UserDAO;
import java.io.IOException;
import java.util.List;


public class UserServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "create":
                    createUser(request, response);
                    break;
                case "update":
                    updateUser(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
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

    private void createUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));
        String sjsuEmail = request.getParameter("sjsuEmail");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = new User();
        user.setSjsuId(sjsuId);
        user.setSjsuEmail(sjsuEmail);
        user.setUsername(username);
        user.setPassword(password);

        userDAO.createUser(user);

        // Redirect or forward to a success page
        response.sendRedirect("success.jsp");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));
        String sjsuEmail = request.getParameter("sjsuEmail");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = new User();
        user.setSjsuId(sjsuId);
        user.setSjsuEmail(sjsuEmail);
        user.setUsername(username);
        user.setPassword(password);

        userDAO.updateUser(user);

        // Redirect or forward to a success page
        response.sendRedirect("success.jsp");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));

        userDAO.deleteUser(sjsuId);

        // Redirect or forward to a success page
        response.sendRedirect("success.jsp");
    }

    private void getUserById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int sjsuId = Integer.parseInt(request.getParameter("sjsuId"));

        User user = userDAO.getUserById(sjsuId);

        // Use the retrieved user as needed, e.g., display it on a JSP page
        request.setAttribute("user", user);
        request.getRequestDispatcher("userDetails.jsp").forward(request, response);
    }

    private void getUserByUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        User user = userDAO.getUserByUsername(username);

        // Use the retrieved user as needed, e.g., display it on a JSP page
        request.setAttribute("user", user);
        request.getRequestDispatcher("userDetails.jsp").forward(request, response);
    }

    private void getAllUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
