package dal;

import model.Review;
import util.DbConnectionInt;
import util.singletonDbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewDAO {
    // DbConnection class that handles the database connection
    private final DbConnectionInt dbConnection;

    public ReviewDAO() {
        dbConnection = singletonDbConnection.getInstance(); // Initialize the database connection
    }

    // Method to insert a new review into the database
    public boolean createReview(Review review) {
        Connection connection = dbConnection.getConnection();
        String insertReviewQuery = "INSERT INTO Review (EventID, SJSUID, ReviewText, Rating) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(insertReviewQuery);
            statement.setInt(1, review.getEventId());
            statement.setInt(2,review.getAttendeeId());
            statement.setString(3, review.getReviewText());
            statement.setFloat(4, review.getRating());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
    public boolean saveRating(int eventId, int userId, int rating) {
        Connection connection = null;
        PreparedStatement checkStatement = null;
        PreparedStatement updateStatement = null;

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
                String insertQuery = "INSERT INTO Review (EventID, SJSUID, Rating) VALUES (?, ?, ?)";
                updateStatement = connection.prepareStatement(insertQuery);
                updateStatement.setInt(1, eventId);
                updateStatement.setInt(2, userId);
                updateStatement.setInt(3, rating);
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

    // Methods for updating and deleting reviews could be implemented later
}
