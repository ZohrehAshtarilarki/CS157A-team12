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
        String checkReviewQuery = "SELECT COUNT(*) FROM Review WHERE EventID = ? AND SJSUID = ?";
        try {
            // Check if review already exists
            PreparedStatement checkStatement = connection.prepareStatement(checkReviewQuery);
            checkStatement.setInt(1, review.getEventId());
            checkStatement.setInt(2, review.getSjsuId());
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                // Review already exists, return false or handle as needed
                return false;
            }
            // If no existing review, insert new review
            String insertReviewQuery = "INSERT INTO Review (EventID, SJSUID, ReviewText, Rating) VALUES (?, ?, ?, ?)";
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

        String deleteReviewQuery = "DELETE FROM Review WHERE ReviewID = ?";

        try {
            // Delete from Review table
            System.out.println("Preparing to delete from Review table.");
            PreparedStatement preparedStatement = connection.prepareStatement(deleteReviewQuery);
            preparedStatement.setInt(1, reviewId);
            int reviewDeleteCount = preparedStatement.executeUpdate();
            System.out.println("Rows deleted from Attendee table: " + reviewDeleteCount);

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
        String query = "SELECT r.* FROM Review r " +
                "JOIN Event e ON r.EventID = e.EventID " +
                "JOIN Manage m ON e.EventID = m.EventID " +
                "WHERE m.SJSUID = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, sjsuId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Review review = new Review();
                    review.setReviewId(resultSet.getInt("reviewId"));
                    review.setEventId(resultSet.getInt("eventId"));
                    review.setSjsuId(resultSet.getInt("sjsuId"));
                    review.setReviewText(resultSet.getString("reviewText"));
                    review.setRating(resultSet.getFloat("rating"));
                    reviews.add(review);
                }
            }
        }
        return reviews;
    }


    // Method to retrieve a review by its ID
    public Review getReviewById(int reviewId) {
        Connection connection = dbConnection.getConnection();
        String selectQuery = "SELECT * FROM Review WHERE ReviewID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.setInt(1, reviewId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Review(
                        resultSet.getInt("EventID"),
                        resultSet.getInt("AttendeeID"),
                        resultSet.getString("ReviewText"),
                        resultSet.getFloat("Rating"));
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
  This implementation checks if a review exists for a specific event and user.
  If it does, it updates the Rating. If not, it creates a new review with the given Rating.
   */
    public boolean saveReview(int eventId, int userId, int rating, String text) {
        Connection connection = null;
        PreparedStatement checkStatement = null;
        PreparedStatement updateStatement = null;

        System.out.println("Here in save rating");
        System.out.println("Here in save rating:" + text);

        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // Check if a review already exists
            String checkQuery = "SELECT COUNT(*) FROM Review WHERE EventID = ? AND SJSUID = ?";
            checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, eventId);
            checkStatement.setInt(2, userId);

            ResultSet rs = checkStatement.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // Update existing review with new rating
                String updateQuery = "UPDATE Review SET Rating = ? WHERE EventID = ? AND SJSUID = ?";
                updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setInt(1, rating);
                updateStatement.setInt(2, eventId);
                updateStatement.setInt(3, userId);
            } else {
                // Insert new review with rating
                String insertQuery = "INSERT INTO Review (EventID, SJSUID, Rating, ReviewText) VALUES (?, ?, ?, ?)";
                updateStatement = connection.prepareStatement(insertQuery);
                updateStatement.setInt(1, eventId);
                updateStatement.setInt(2, userId);
                updateStatement.setInt(3, rating);
                updateStatement.setString(4, text);
            }

            updateStatement.executeUpdate();
            connection.commit(); // Commit transaction
            return true;
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
            if (updateStatement != null) try { updateStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public double getAverageRatingForEvent(int eventId) {
        double averageRating = 0.0;
        String sql = "SELECT AVG(Rating) AS Average FROM Review WHERE EventID = ?";
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
        String sql = "SELECT COUNT(*) AS Count FROM Review WHERE EventID = ?";
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
        String selectQuery = "SELECT * FROM Review";

        List<Review> reviewList = new ArrayList<>();
        Review review;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                review = new Review();
                review.setReviewId(resultSet.getInt("reviewId"));
                review.setEventId(resultSet.getInt("eventId"));
                review.setSjsuId(resultSet.getInt("sjsuId"));
                review.setReviewText(resultSet.getString("reviewText"));
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

    public Review getReviewByText(String reviewText) {
        Connection connection = dbConnection.getConnection();
        String selectQuery = "SELECT * FROM Review WHERE ReviewText = ?";
        Review review = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, String.valueOf(reviewText));

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int attendeeId = Integer.parseInt(resultSet.getString("attendeeId"));
                int eventId = Integer.parseInt((resultSet.getString("eventId")));
                String reviewText1 = resultSet.getString("reviewText");
                float rating = resultSet.getFloat("rating");

                review = new Review(eventId, attendeeId, reviewText1, rating);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        } finally {
            dbConnection.closeConnection();
        }
        return review;
    }
}
