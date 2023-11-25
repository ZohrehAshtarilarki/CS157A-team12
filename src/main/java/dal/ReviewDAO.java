package dal;

import model.Review;
import util.DbConnectionInt;
import util.singletonDbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewDAO {
    private final DbConnectionInt dbConnection;
    // Method to get the database connection
    public ReviewDAO() {
        dbConnection = singletonDbConnection.getInstance();;
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

    // Methods for updating and deleting reviews will be implemented later
}
