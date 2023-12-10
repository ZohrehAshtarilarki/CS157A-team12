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

    public boolean updateOrganizer(EventOrganizer eventOrganizer) {
        if (eventOrganizer == null) {
            // Early return if the eventOrganizer object is null
            return false;
        }

        Connection connection = null;
        boolean updateResult = false;

        String updateOrganizerQuery = "UPDATE EventOrganizer SET OrganizationName=? WHERE SJSUID=?";
        String updateUserQuery = "UPDATE User SET SJSUEmail=?, Username=?, Password=?, Role=? WHERE SJSUID=?";

        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // Update User table
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateUserQuery)) {
                preparedStatement.setString(1, eventOrganizer.getSjsuEmail());
                preparedStatement.setString(2, eventOrganizer.getUsername());
                preparedStatement.setString(3, eventOrganizer.getPassword());
                preparedStatement.setString(4, eventOrganizer.getRole());
                preparedStatement.setInt(5, eventOrganizer.getSjsuId());

                int userAffectedRows = preparedStatement.executeUpdate();
                updateResult = userAffectedRows > 0;
            }

            // Update EventOrganizer table
            try (PreparedStatement preparedStatement2 = connection.prepareStatement(updateOrganizerQuery)) {
                preparedStatement2.setString(1, eventOrganizer.getOrganizationName());
                preparedStatement2.setInt(2, eventOrganizer.getSjsuId());

                int organizerAffectedRows = preparedStatement2.executeUpdate();
                updateResult = updateResult && organizerAffectedRows > 0;
            }

            connection.commit(); // Commit transaction if all updates are successful

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback transaction in case of an error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // Handle rollback exception appropriately
                }
            }
            e.printStackTrace();
            // Handle exceptions appropriately
        } finally {
            if (connection != null) {
                try {
                    connection.close(); // Ensure connection is closed
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle closing exception appropriately
                }
            }
        }
        return updateResult;
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
}
