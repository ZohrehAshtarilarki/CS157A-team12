package controller;

import jakarta.servlet.RequestDispatcher;
import model.Review;
import dal.ReviewDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ReviewServlet")
public class ReviewServlet extends HttpServlet {

    private ReviewDAO reviewDAO;

    @Override
    public void init() {
        reviewDAO = new ReviewDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // Retrieve parameters from the request
        String eventIdStr = request.getParameter("eventId");
        String attendeeIdStr = request.getParameter("attendeeId");
        String reviewText = request.getParameter("reviewText");
        String ratingStr = request.getParameter("rating");

        int eventId, attendeeId;
        float rating;

        // Try to parse eventId and attendeeId from the request parameters
        try {
            eventId = Integer.parseInt(eventIdStr);
            attendeeId = Integer.parseInt(attendeeIdStr);
            rating = Float.parseFloat(ratingStr);
        } catch (NumberFormatException e) {
            // Handle the case where eventId or attendeeId is not a valid integer
            request.setAttribute("errorMessage", "Invalid event ID or attendee ID.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/errorPage.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            rating = Float.parseFloat(ratingStr);
        } catch (NumberFormatException e) {
            // Handle the case where rating is not a valid float
            request.setAttribute("errorMessage", "Invalid rating format.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/errorPage.jsp");
            dispatcher.forward(request, response);
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



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
    // Other methods (PUT, DELETE) for updating and deleting reviews
}
