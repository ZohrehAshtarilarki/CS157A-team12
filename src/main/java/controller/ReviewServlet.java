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
            rating = Float.parseFloat(ratingStr);
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

        String eventIdParam = request.getParameter("eventId");
        String ratingParam = request.getParameter("rating");
        String textParam = request.getParameter("reviewText");

        if (eventIdParam == null || ratingParam == null) {
            request.setAttribute("errorMessage", "Invalid event ID or rating.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/eventInfo.jsp");
            dispatcher.forward(request, response);
            return;
        }

        int eventId, rating;
        String text;

        try {
            eventId = Integer.parseInt(eventIdParam);
            rating = Integer.parseInt(ratingParam);
            text = textParam;
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid event ID or rating format.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/eventInfo.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Check if the user is registered for the event
        EventDAO eventDAO = new EventDAO();
        boolean isRegistered = eventDAO.isUserRegisteredForEvent(sjsuId, eventId);
        if (!isRegistered) {
            // User is not registered for the event, hence cannot submit a review
            request.setAttribute("errorMessage", "You must be registered for the event to submit a review.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/eventInfo.jsp");
            dispatcher.forward(request, response);
            return;
        }

        ReviewDAO reviewDAO = new ReviewDAO();
        boolean result = reviewDAO.saveReview(eventId, sjsuId, rating, text);


        if (result) {
            // Fetch the event details again to include in the forwarded request
            Event event = eventDAO.getEventById(eventId);
            if (event != null) {
                // Set the event object in the request scope
                request.setAttribute("event", event);
                // Set a success message
                request.setAttribute("successMessage", "Rating submitted successfully!");
                // Also set the eventId in the request attribute
                request.setAttribute("eventId", eventId);
            } else {
                // Set an error message if event is not found
                request.setAttribute("errorMessage", "Event not found.");
            }
        } else {
            // Set an error message in case of failure in saving rating
            request.setAttribute("errorMessage", "There was an error submitting the rating.");
        }

        // Forward to eventInfo.jsp in all cases
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/eventInfo.jsp");
        dispatcher.forward(request, response);
    }


    private void deleteReview(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String reviewIdStr = request.getParameter("reviewId");
        System.out.println("Received Review ID: " + reviewIdStr); // Additional logging

        if (reviewIdStr == null || reviewIdStr.isEmpty()) {
            System.out.println("Review ID is missing or empty");
            request.setAttribute("error", "Review ID is missing or empty.");
            request.getRequestDispatcher("/views/organizerDash.jsp").forward(request, response);
            return;
        }

        try {
            int reviewId = Integer.parseInt(reviewIdStr);
            System.out.println("Attempting to delete review with ID: " + reviewId);
            reviewDAO.deleteReview(reviewId);
            System.out.println("Deletion successful for review ID: " + reviewId);
            request.setAttribute("message", "Review successfully deleted.");
            request.getRequestDispatcher("/views/organizerDash.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Review ID format: " + reviewIdStr);
            request.setAttribute("error", "Invalid Review ID format.");
            request.getRequestDispatcher("/views/organizerDash.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Deletion failed for Review ID: " + reviewIdStr);
            request.setAttribute("error", "Error while deleting review.");
            request.getRequestDispatcher("/views/organizerDash.jsp").forward(request, response);
        }
    }

    private void getReviewById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handling a GET request to retrieve a review
        int reviewId = Integer.parseInt(request.getParameter("reviewId"));
        Review review = reviewDAO.getReviewById(reviewId);

        if (review != null) {
            request.setAttribute("review", review);
            request.getRequestDispatcher("reviewDetails.jsp").forward(request, response);
        } else {
            response.sendRedirect("reviewNotFound.jsp");
        }
    }
    private void getAllReviews(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Assuming the event ID is passed as a request parameter named "eventId"
        //String eventIdStr = request.getParameter("eventId");
        //int eventId = 0;

        // Convert the event ID to an integer
       // try {
            //eventId = Integer.parseInt(eventIdStr);
        //} catch (NumberFormatException e) {
            // Handle the case where eventId is not a valid integer
            // For example, redirect to an error page or set a default event ID
      //  }

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
