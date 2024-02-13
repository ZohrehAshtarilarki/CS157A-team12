package dal;

import model.Ticket;
import util.DbConnectionInt;
import util.singletonDbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {

    private final DbConnectionInt dbConnection;

    public TicketDAO() {
        dbConnection = singletonDbConnection.getInstance();
    }

    public boolean hasTicketForEvent(int sjsuId, int eventId) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT COUNT(*) FROM register WHERE sjsu_id = ? AND event_id = ?")) {

            statement.setInt(1, sjsuId);
            statement.setInt(2, eventId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // Method to create a new ticket
    public boolean createTicket(Ticket ticket) {
        if (!hasTicketForEvent(ticket.getSjsuId(), ticket.getEventId())) {
            try (Connection connection = dbConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "INSERT INTO ticket (event_id, sjsu_id, ticket_barcode) VALUES (?, ?, ?)")) {

                statement.setInt(1, ticket.getEventId());
                statement.setInt(2, ticket.getSjsuId());
                statement.setString(3, ticket.getTicketBarcode());
                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    // Method to retrieve a ticket by its barcode
    public Ticket getTicketByBarcode(String barcode) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM ticket WHERE ticket_barcode= ?")) {

            statement.setString(1, barcode);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Ticket(
                            resultSet.getInt("ticket_id"),
                            resultSet.getInt("event_id"),
                            resultSet.getString("ticket_barcode"),
                            -1); // -1 or any placeholder for sjsuId
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // Method to retrieve tickets by user ID
    public List<Ticket> getTicketsByUserID(int sjsuId) {
        List<Ticket> tickets = new ArrayList<>();
        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT *  FROM ticket" +
                            " WHERE sjsu_id= ?");

            statement.setInt(1, sjsuId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    tickets.add(new Ticket(
                            resultSet.getInt("ticket_id"),
                            resultSet.getInt("event_id"),
                            resultSet.getString("ticket_barcode"),
                            sjsuId)); // Using sjsuID from the method parameter
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    // Method to retrieve a ticket by its ID
    public Ticket getTicketById(String ticketId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "SELECT * FROM ticket WHERE ticket_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, ticketId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Ticket(
                        resultSet.getInt("ticket_id"),
                        resultSet.getInt("event_id"),
                        resultSet.getString("ticket_barcode"),
                        -1); // -1 or any placeholder for sjsuId
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        } finally {
            dbConnection.closeConnection();
        }
        return null;
    }


}
