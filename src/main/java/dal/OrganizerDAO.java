package dal;

import model.Organizer;
import util.DbConnectionInt;
import util.singletonDbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class OrganizerDAO {
    private DbConnectionInt dbConnection;

    public OrganizerDAO() {
        dbConnection = singletonDbConnection.getInstance();
    }

    public void createOrganizer(Organizer organizer) {
        Connection connection = dbConnection.getConnection();
        String insertQuery = "INSERT INTO Organizer (SJSUID, OrganizerID, OrganizationName) VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, organizer.getSjsuId());
            preparedStatement.setInt(2, organizer.getOrganizerId());
            preparedStatement.setString(3, organizer.getOrganizationName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        } finally {
            dbConnection.closeConnection();
        }
    }

    public void updateOrganizer(Organizer organizer) {
        Connection connection = dbConnection.getConnection();
        String updateQuery = "UPDATE Organizer SET OrganizerID=?, OrganizationName=? WHERE SJSUID=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setInt(1, organizer.getOrganizerId());
            preparedStatement.setString(2, organizer.getOrganizationName());
            preparedStatement.setInt(3, organizer.getSjsuId());

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
        String deleteQuery = "DELETE FROM Organizer WHERE SJSUID = ?";

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

    public Organizer getOrganizerById(int sjsuId) {
        Connection connection = dbConnection.getConnection();
        String selectQuery = "SELECT * FROM Organizer WHERE SJSUID = ?";
        Organizer organizer = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, sjsuId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                organizer = new Organizer();
                organizer.setSjsuId(resultSet.getInt("SJSUID"));
                organizer.setOrganizerId(resultSet.getInt("OrganizerID"));
                organizer.setOrganizationName(resultSet.getString("OrganizationName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        } finally {
            dbConnection.closeConnection();
        }

        return organizer;
    }

    public List<Organizer> getAllOrganizers() {
        Connection connection = dbConnection.getConnection();
        String selectQuery = "SELECT * FROM Organizer";
        List<Organizer> organizerList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Organizer organizer = new Organizer();
                organizer.setSjsuId(Integer.parseInt(resultSet.getString("SJSUID")));
                organizer.setOrganizerId(Integer.parseInt(resultSet.getString("OrganizerID")));
                organizer.setOrganizationName(resultSet.getString("OrganizationName"));

                organizerList.add(organizer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        } finally {
            dbConnection.closeConnection();
        }

        return organizerList;
    }
}
