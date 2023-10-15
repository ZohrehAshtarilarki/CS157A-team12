package dal;

import model.Attendee;
import model.User;
import util.DBConnectionInt;
import util.singletonDBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendeeDAO {
    private DBConnectionInt dbConnection;

    public AttendeeDAO() {
        dbConnection = singletonDBConnection.getInstance();
    }

    public void createAttendee(Attendee attendee) {
        Connection connection = dbConnection.getConnection();
        String insertQuery = "INSERT INTO Attendee (SJSUID, AttendeeID) VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, attendee.getSjsuId());
            preparedStatement.setInt(2, attendee.getAttendeeId());

            preparedStatement.executeUpdate();
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
                attendee = new Attendee();
                attendee.setSjsuId(Integer.parseInt(resultSet.getString("SJSUID")));
                attendee.setAttendeeId(Integer.parseInt(resultSet.getString("AttendeeID")));
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

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Attendee attendee = new Attendee();
                attendee.setSjsuId(Integer.parseInt(resultSet.getString("SJSUID")));
                attendee.setAttendeeId(Integer.parseInt(resultSet.getString("AttendeeID")));

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

