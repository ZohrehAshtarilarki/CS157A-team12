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

    public void updateAttendee(Attendee attendee) {
        Connection connection = dbConnection.getConnection();
        String updateQuery = "UPDATE Attendee SET AttendeeID=? WHERE SJSUID=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setInt(1, attendee.getAttendeeId());
            preparedStatement.setInt(2, attendee.getSjsuId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        } finally {
            dbConnection.closeConnection();
        }
    }

    public void deleteAttendee(int sjsuId) {
        Connection connection = dbConnection.getConnection();
        String deleteQuery = "DELETE FROM Attendee WHERE SJSUID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, sjsuId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        } finally {
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
        String selectQuery = "SELECT * FROM Attendee";
        List<Attendee> attendeeList = new ArrayList<>();
        Attendee attendee;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int sjsuID = Integer.parseInt(resultSet.getString("SJSUID"));
                String sjsuEmail = resultSet.getString("SJSUEmail");
                String userName = resultSet.getString("Username");
                String password = resultSet.getString("Password");
                String role = resultSet.getString("Role");

                attendee = new Attendee(sjsuID, sjsuEmail, userName, password, role);

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
}

