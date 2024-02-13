package dal;

import model.Attendee;
import model.Review;
import util.DbConnectionInt;
import util.singletonDbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {
    // DbConnection class that handles the database connection
    private final DbConnectionInt dbConnection;

    public ReviewDAO() {
        dbConnection = singletonDbConnection.getInstance(); // Initialize the database connection
    }

    // Method to insert a new review into the database
    public boolean createReview(Review review) {
        Connection connection = dbConnection.getConnection();

        try {
            // If no existing review, insert new review
            String insertReviewQuery = "INSERT INTO review (event_id, sjsu_id, review_text, rating) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertReviewQuery);
            statement.setInt(1, review.getEventId());
            statement.setInt(2,review.getSjsuId());
            statement.setString(3, review.getReviewText());
            statement.setFloat(4, review.getRating());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void deleteReview(int reviewId) throws SQLException {
        Connection connection = dbConnection.getConnection();
        // Start a transaction
        connection.setAutoCommit(false);

        String deleteReviewQuery = "DELETE FROM review WHERE review_id = ?";

        try {
            // Delete from Review table
            PreparedStatement preparedStatement = connection.prepareStatement(deleteReviewQuery);
            preparedStatement.setInt(1, reviewId);
            int reviewDeleteCount = preparedStatement.executeUpdate();

            // Commit transaction
            connection.commit();
        } catch (SQLException e) {
            // If there is an error, rollback the transaction
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            // Handle exceptions appropriately
        } finally {
            try {
                // Reset default commit behavior
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dbConnection.closeConnection();
        }
    }

    public List<Review> getReviewsByOrganizer(int sjsuId) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT r.* FROM review r " +
                "JOIN event e ON r.event_id = e.event_id " +
                "JOIN manage m ON e.event_id = m.event_id " +
                "WHERE m.sjsu_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, sjsuId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Review review = new Review();
                    review.setReviewId(resultSet.getInt("review_id"));
                    review.setEventId(resultSet.getInt("event_id"));
                    review.setSjsuId(resultSet.getInt("sjsu_id"));
                    review.setReviewText(resultSet.getString("review_text"));
                    review.setRating(resultSet.getFloat("rating"));
                    reviews.add(review);
                }
            }
        }
        return reviews;
    }

    /*
  This implementation checks if a review exists for a specific event and user.
  If it does, it updates the Rating. If not, it creates a new review with the given Rating.
   */
    public boolean saveReview(int eventId, int userId, int rating, String text) {
        Connection connection = null;
        PreparedStatement checkStatement = null;
        PreparedStatement insertStatement = null;

        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // Check if user is registered for the event
            String checkQuery = "SELECT COUNT(*) FROM register WHERE event_id = ? AND sjsu_id = ?";
            checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, eventId);
            checkStatement.setInt(2, userId);

            ResultSet rs = checkStatement.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // User is registered for the event, proceed with saving the review
                String insertQuery = "INSERT INTO review (event_id, sjsu_id, rating, review_text) VALUES (?, ?, ?, ?)";
                insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setInt(1, eventId);
                insertStatement.setInt(2, userId);
                insertStatement.setInt(3, rating);
                insertStatement.setString(4, text);

                insertStatement.executeUpdate();
                connection.commit(); // Commit transaction
                return true;
            } else {
                // User is not registered for the event, rollback and return false
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            // Close resources
            if (checkStatement != null) try { checkStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (insertStatement != null) try { insertStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }


    public double getAverageRatingForEvent(int eventId) {
        double averageRating = 0.0;
        String sql = "SELECT AVG(Rating) AS Average FROM review WHERE event_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, eventId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                averageRating = resultSet.getDouble("Average");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return averageRating;
    }

    public int getRatingCountForEvent(int eventId) {
        int ratingCount = 0;
        String sql = "SELECT COUNT(*) AS Count FROM review WHERE event_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, eventId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                ratingCount = resultSet.getInt("Count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ratingCount;
    }

    public List<Review> getAllReviews() {
        Connection connection = dbConnection.getConnection();
        String selectQuery = "SELECT * FROM review";

        List<Review> reviewList = new ArrayList<>();
        Review review;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                review = new Review();
                review.setReviewId(resultSet.getInt("review_d"));
                review.setEventId(resultSet.getInt("event_id"));
                review.setSjsuId(resultSet.getInt("sjsu_id"));
                review.setReviewText(resultSet.getString("review_text"));
                review.setRating(resultSet.getFloat("rating"));

                reviewList.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        } finally {
            dbConnection.closeConnection();
        }

        return reviewList;
    }

    // Method to retrieve a review by its ID
    public List<Review> getReviewById(int eventId) {
        Connection connection = dbConnection.getConnection();
        String selectQuery = "SELECT * FROM review WHERE event_id = " + eventId;
        List<Review> reviewList = new ArrayList<>();
        Review review;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                review = new Review();
                review.setReviewId(resultSet.getInt("review_id"));
                review.setEventId(resultSet.getInt("event_id"));
                review.setSjsuId(resultSet.getInt("sjsu_id"));
                review.setReviewText(resultSet.getString("review_text"));
                review.setRating(resultSet.getFloat("rating"));

                reviewList.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        } finally {
            dbConnection.closeConnection();
        }

        return reviewList;
    }
}
