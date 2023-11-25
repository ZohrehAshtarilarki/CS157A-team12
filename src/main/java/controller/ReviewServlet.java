package controller;

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
            throws IOException {
        // Handling a POST request to add a new review
        int eventId = Integer.parseInt(request.getParameter("eventId"));
        int attendeeId = Integer.parseInt(request.getParameter("attendeeId"));
        String reviewText = request.getParameter("reviewText");
        float rating = Float.parseFloat(request.getParameter("rating"));

        Review review = new Review(eventId, attendeeId, reviewText, rating);
        boolean result = reviewDAO.createReview(review);

        if (result) {
            response.sendRedirect("/views/eventInfo.jsp");
        } else {
            response.sendRedirect("/views/eventInfo.jsp");
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
