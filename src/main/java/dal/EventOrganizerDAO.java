package dal;

import model.EventOrganizer;
import util.DbConnectionInt;
import util.singletonDbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class EventOrganizerDAO {
    private final DbConnectionInt dbConnection;

    public EventOrganizerDAO() {
        dbConnection = singletonDbConnection.getInstance();
    }

    public void createOrganizer(EventOrganizer eventOrganizer) {
        Connection connection = dbConnection.getConnection();
        String insertQuery = "INSERT INTO EventOrganizer (SJSUID, OrganizationName) VALUES (?, ?)";

        try {
            PreparedStatement OrganizerStm = connection.prepareStatement(insertQuery);
            OrganizerStm.setInt(1, eventOrganizer.getSjsuId());
            OrganizerStm.setString(2, eventOrganizer.getOrganizationName());

            OrganizerStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        } finally {
            dbConnection.closeConnection();
        }
    }

    public void updateOrganizer(EventOrganizer eventOrganizer) {
        Connection connection = dbConnection.getConnection();
        String updateQuery = "UPDATE EventOrganizer SET OrganizerID=?, OrganizationName=? WHERE SJSUID=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            //preparedStatement.setInt(1, eventOrganizer.getOrganizerId());
            preparedStatement.setString(2, eventOrganizer.getOrganizationName());
            preparedStatement.setInt(3, eventOrganizer.getSjsuId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        } finally {
            dbConnection.closeConnection();
        }
    }

    public void deleteOrganizer(int sjsuId) {
        Connection connection = dbConnection.getConnection();
        String deleteQuery = "DELETE FROM EventOrganizer WHERE SJSUID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, sjsuId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        } finally {
            dbConnection.closeConnection();
        }
    }

    public EventOrganizer getOrganizerById(int sjsuId) {
        Connection connection = dbConnection.getConnection();
        String selectQuery = "SELECT * FROM EventOrganizer WHERE SJSUID = ?";
        EventOrganizer eventOrganizer = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, sjsuId);
            
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                eventOrganizer = new EventOrganizer();
                eventOrganizer.setSjsuId(resultSet.getInt("SJSUID"));
                eventOrganizer.setOrganizationName(resultSet.getString("OrganizationName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        } finally {
            dbConnection.closeConnection();
        }

        return eventOrganizer;
    }

    public List<EventOrganizer> getAllOrganizers() {
        Connection connection = dbConnection.getConnection();
        String selectQuery = "SELECT * FROM EventOrganizer";
        List<EventOrganizer> eventOrganizerList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                EventOrganizer eventOrganizer = new EventOrganizer();
                eventOrganizer.setSjsuId(Integer.parseInt(resultSet.getString("SJSUID")));
                eventOrganizer.setOrganizerId(Integer.parseInt(resultSet.getString("OrganizerID")));
                eventOrganizer.setOrganizationName(resultSet.getString("OrganizationName"));

                eventOrganizerList.add(eventOrganizer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        } finally {
            dbConnection.closeConnection();
        }

        return eventOrganizerList;
    }
}
