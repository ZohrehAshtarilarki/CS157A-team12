package dal;

import model.Attendee;
import util.DbConnectionInt;
import util.singletonDbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendeeDAO {
    private final DbConnectionInt dbConnection;

    public AttendeeDAO() {
        dbConnection = singletonDbConnection.getInstance();
    }


    public void createAttendee(Attendee attendee) {
        Connection connection = dbConnection.getConnection();
        String insertQuery = "INSERT INTO Attendee (SJSUID) VALUES (?)";

        try {
            // Start transaction
            connection.setAutoCommit(false);

            //AttendeeID is auto-generated, we don't need to set it manually
            PreparedStatement AttendeeStm = connection.prepareStatement(insertQuery);
            AttendeeStm.setInt(1, attendee.getSjsuId());

            AttendeeStm.executeUpdate();

            // Commit transaction
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        } finally {
            dbConnection.closeConnection();
        }
    }

    public boolean updateAttendee(Attendee attendee) {
        Connection connection = dbConnection.getConnection();
        boolean updateResult = false;

        // SQL query to update the User table
        String updateUserQuery = "UPDATE User SET SJSUEmail=?, Username=?, Password=?, Role=? WHERE SJSUID=?";

        PreparedStatement preparedStatement = null;
        try {
            // Prepare the statement for updating the User table
            preparedStatement = connection.prepareStatement(updateUserQuery);
            preparedStatement.setString(1, attendee.getSjsuEmail());
            preparedStatement.setString(2, attendee.getUsername());
            preparedStatement.setString(3, attendee.getPassword());
            preparedStatement.setString(4, attendee.getRole());
            preparedStatement.setInt(5, attendee.getSjsuId());

            // Execute the update
            int affectedRows = preparedStatement.executeUpdate();
            updateResult = affectedRows > 0; // true if the update affected at least one row

        } catch (SQLException e) {
            e.printStackTrace();
            // Consider logging this exception or handle it appropriately
        } finally {
            // Close resources
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            dbConnection.closeConnection();
        }

        return updateResult;
    }

    public void deleteAttendee(int sjsuId) throws SQLException {
        Connection connection = dbConnection.getConnection();
        // Start a transaction
        connection.setAutoCommit(false);

        String deleteAttendeeQuery = "DELETE FROM Attendee WHERE SJSUID = ?";
        String deleteUserQuery = "DELETE FROM User WHERE SJSUID = ?";

        try {
            // Delete from Attendee table
            System.out.println("Preparing to delete from Attendee table.");
            PreparedStatement preparedStatement = connection.prepareStatement(deleteAttendeeQuery);
            preparedStatement.setInt(1, sjsuId);
            int attendeeDeleteCount = preparedStatement.executeUpdate();
            System.out.println("Rows deleted from Attendee table: " + attendeeDeleteCount);

            // Delete from User table
            System.out.println("Preparing to delete from User table.");
            preparedStatement = connection.prepareStatement(deleteUserQuery);
            preparedStatement.setInt(1, sjsuId);
            int userDeleteCount = preparedStatement.executeUpdate();
            System.out.println("Rows deleted from User table: " + userDeleteCount);

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


    public Attendee getAttendeeById(int sjsuId) {
        Connection connection = dbConnection.getConnection();
        String selectQuery = "SELECT * FROM Attendee WHERE SJSUID = ?";
        Attendee attendee = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, sjsuId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int sjsuID = Integer.parseInt(resultSet.getString("SJSUID"));
                String sjsuEmail = resultSet.getString("SJSUEmail");
                String userName = resultSet.getString("Username");
                String password = resultSet.getString("Password");
                String role = resultSet.getString("Role");

                attendee = new Attendee(sjsuID, sjsuEmail, userName, password, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        } finally {
            dbConnection.closeConnection();
        }

        return attendee;
    }

    public List<Attendee> getAllAttendees() {
        Connection connection = dbConnection.getConnection();
        String selectQuery = "SELECT Attendee.SJSUID, User.SJSUEmail, User.UserName, User.Password, User.Role " +
                             "FROM Attendee NATURAL JOIN User";

        List<Attendee> attendeeList = new ArrayList<>();
        Attendee attendee;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                attendee = new Attendee();
                attendee.setSjsuId(resultSet.getInt("SJSUID"));
                attendee.setSjsuEmail(resultSet.getString("SJSUEmail"));
                attendee.setUsername(resultSet.getString("Username"));
                attendee.setPassword(resultSet.getString("Password"));
                attendee.setRole(resultSet.getString("Role"));

                attendeeList.add(attendee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        } finally {
            dbConnection.closeConnection();
        }

        return attendeeList;
    }

    public Attendee getAttendeeByName(String username) {
        Connection connection = dbConnection.getConnection();
        String selectQuery = "SELECT * FROM Attendee WHERE SJSUID = ?";
        Attendee attendee = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, String.valueOf(username));

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int sjsuID = Integer.parseInt(resultSet.getString("SJSUID"));
                String sjsuEmail = resultSet.getString("SJSUEmail");
                String userName = resultSet.getString("Username");
                String password = resultSet.getString("Password");
                String role = resultSet.getString("Role");

                attendee = new Attendee(sjsuID, sjsuEmail, userName, password, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        } finally {
            dbConnection.closeConnection();
        }

        return attendee;
    }
}

