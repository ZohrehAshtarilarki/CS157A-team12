package controller;

import dal.EventDAO;
import dal.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;
import model.Attendee;
import model.Event;
import model.Review;
import dal.ReviewDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ReviewServlet", urlPatterns = { "/ReviewServlet" })
public class ReviewServlet extends HttpServlet {

    private ReviewDAO reviewDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        reviewDAO = new ReviewDAO();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "createReview":
                    createReview(request, response);
                    break;
                case "deleteReview":
                    deleteReview(request, response);
                    break;
                case "saveReview":
                    saveReview(request, response);
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
                case "getReviewById":
                    getReviewById(request, response);
                    break;
                case "getAllReviews":
                    getAllReviews(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        }
    }
    private void createReview(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Retrieve parameters from the request
        String eventIdStr = request.getParameter("eventId");
        String sjsuId = request.getParameter("sjsuId");
        String reviewText = request.getParameter("reviewText");
        String ratingStr = request.getParameter("rating");

        int eventId, attendeeId;
        float rating;

        // Try to parse eventId and attendeeId from the request parameters
        try {
            eventId = Integer.parseInt(eventIdStr);
            attendeeId = Integer.parseInt(sjsuId);
        } catch (NumberFormatException e) {
            // Handle the case where eventId or attendeeId is not a valid integer
            request.setAttribute("errorMessage", "Invalid event ID or attendee ID.");
            return;
        }

        try {
            rating = Float.parseFloat(ratingStr);
        } catch (NumberFormatException e) {
            // Handle the case where rating is not a valid float
            request.setAttribute("errorMessage", "Invalid rating format.");
            return;
        }

        // Create a new review object and save it
        Review review = new Review(eventId, attendeeId, reviewText, rating);
        boolean result = reviewDAO.createReview(review);

        // Calculate the average rating and count
        double averageRating = reviewDAO.getAverageRatingForEvent(eventId);
        int ratingCount = reviewDAO.getRatingCountForEvent(eventId);

        // Set the average rating and count as request attributes
        request.setAttribute("averageRating", averageRating);
        request.setAttribute("ratingCount", ratingCount);

        if (result) {
            // Forward to eventInfo.jsp with the included data
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/eventInfo.jsp?eventId=" + eventId);
            dispatcher.forward(request, response);
        } else {
            // Set an error message and forward back to eventInfo.jsp
            request.setAttribute("errorMessage", "There was an error submitting your review. Please try again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/eventInfo.jsp?eventId=" + eventId);
            dispatcher.forward(request, response);
        }
    }

    private void saveReview(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        // Retrieve sjsuId from session
        Integer sjsuId = (Integer) session.getAttribute("SJSUID");

        if (sjsuId == null) {
            // Redirect to login page if sjsuId is not available in the session
            response.sendRedirect(request.getContextPath() + "/views/login.jsp");
            return;
        }

        String eventIdParam = request.getParameter("eventID");
        String ratingParam = request.getParameter("rating");
        String textParam = request.getParameter("reviewText");

        if (eventIdParam == null || ratingParam == null || textParam == null || textParam.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Invalid event ID, rating, or review text.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/eventInfo.jsp");
            dispatcher.forward(request, response);
            return;
        }

        int eventId;
        int rating;
        String text = textParam.trim();

        try {
            eventId = Integer.parseInt(eventIdParam);
            rating = Integer.parseInt(ratingParam);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid event ID or rating format.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/eventInfo.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Initialize EventDAO and ReviewDAO here
        EventDAO eventDAO = new EventDAO();
        ReviewDAO reviewDAO = new ReviewDAO();

        // Check if the user is registered for the event
        boolean isRegistered = eventDAO.isUserRegisteredForEvent(sjsuId, eventId);

        if (!isRegistered) {
            // User is not registered for the event, hence cannot submit a review
            request.setAttribute("errorMessage", "You must be registered for the event to submit a review.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/eventInfo.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // User is registered, proceed with saving the review
        boolean result = reviewDAO.saveReview(eventId, sjsuId, rating, text);

        // Fetch the event details again to include in the forwarded request
        Event event = eventDAO.getEventById(eventId);
        request.setAttribute("event", event);
        request.setAttribute("eventId", eventId);

        if (result) {
            // Set a success message
            request.setAttribute("successMessage", "Review submitted successfully!");
        } else {
            // Set an error message in case of failure in saving the review
            request.setAttribute("errorMessage", "There was an error submitting the review.");
        }

        // Forward to eventInfo.jsp in all cases
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/eventInfo.jsp");
        dispatcher.forward(request, response);
    }


    private void deleteReview(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String reviewIdStr = request.getParameter("reviewId");

        if (reviewIdStr == null || reviewIdStr.isEmpty()) {
            request.setAttribute("error", "Review ID is missing or empty.");
            request.getRequestDispatcher("/views/organizerDash.jsp").forward(request, response);
            return;
        }

        try {
            int reviewId = Integer.parseInt(reviewIdStr);
            reviewDAO.deleteReview(reviewId);
            request.setAttribute("message", "Review successfully deleted.");
            request.getRequestDispatcher("/views/organizerDash.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid Review ID format.");
            request.getRequestDispatcher("/views/organizerDash.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error while deleting review.");
            request.getRequestDispatcher("/views/organizerDash.jsp").forward(request, response);
        }
    }

    private void getReviewById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handling a GET request to retrieve a review
        int reviewId = Integer.parseInt(request.getParameter("reviewId"));
        List<Review> list = reviewDAO.getReviewById(reviewId);

        // Use the retrieved list of reviews as needed, e.g., display it on a JSP page
        request.setAttribute("reviewList", list);
        request.getRequestDispatcher("/views/organizerDash.jsp").forward(request, response);
    }
    private void getAllReviews(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Review> list = reviewDAO.getAllReviews();

        // Use the retrieved list of reviews as needed, e.g., display it on a JSP page
        request.setAttribute("reviewList", list);
        request.getRequestDispatcher("/views/organizerDash.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        // Clean up resources if needed
        reviewDAO = null;
    }
}
