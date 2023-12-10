package controller;

import java.io.IOException;

import dal.NotificationDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Notification;

@WebServlet(name = "NotificationServlet", urlPatterns = { "/NotificationServlet" })
public class NotificationServlet extends HttpServlet {
    private NotificationDAO notificationDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        notificationDAO = new NotificationDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "addNotification":
                    addNotification(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        }
    }

    private void addNotification(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int eventID = Integer.parseInt(request.getParameter("eventID"));
        String text = request.getParameter("notificationText");
        Notification noti = new Notification(eventID,text);
        notificationDAO.addNotification(noti);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/home.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    public void destroy() {
        notificationDAO = null;
    }
}
