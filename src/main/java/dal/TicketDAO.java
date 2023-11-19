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

    public boolean hasTicketForEvent(int sjsuID, int eventId) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT COUNT(*) FROM Register WHERE SJSUID = ? AND EventID = ?")) {

            statement.setInt(1, sjsuID);
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
                         "INSERT INTO Ticket (EventID, TicketBarcode) VALUES (?, ?)")) {

                statement.setInt(1, ticket.getEventId());
                statement.setString(2, ticket.getTicketBarcode());
                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Ticket already exists for this event and user");
        }
        return false;
    }

    // Method to retrieve a ticket by its barcode
    public Ticket getTicketByBarcode(String barcode) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Ticket WHERE TicketBarcode = ?")) {

            statement.setString(1, barcode);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Ticket(
                            resultSet.getInt("TicketID"),
                            resultSet.getInt("EventID"),
                            resultSet.getString("TicketBarcode"),
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
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT t.TicketID, t.EventID, t.TicketBarcode FROM Ticket t " +
                             "NATURAL JOIN Register r WHERE r.SJSUID = ?")) {

            statement.setInt(1, sjsuId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    tickets.add(new Ticket(
                            resultSet.getInt("TicketID"),
                            resultSet.getInt("EventID"),
                            resultSet.getString("TicketBarcode"),
                            sjsuId)); // Using sjsuID from the method parameter
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }
    public List<Ticket> getTicketsByEventId(int eventId) {
        List<Ticket> tickets = new ArrayList<>();
        Connection connection = dbConnection.getConnection();
        String sql = "SELECT t.TicketID, t.EventID, t.TicketBarcode FROM Ticket t " +
                "NATURAL JOIN Register r " +
                "WHERE r.SJSUID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, eventId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Ticket ticket = new Ticket(
                        resultSet.getInt("TicketID"),
                        resultSet.getInt("EventID"),
                        resultSet.getString("TicketBarcode"),
                        -1); // -1 or any placeholder for sjsuId
                // Add the ticket to the list
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            System.out.println("DB operation failure. reason:\n");
            e.printStackTrace();
            // Handle exceptions appropriately later
        } finally {
            dbConnection.closeConnection();
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
            String sql = "SELECT * FROM Ticket WHERE TicketID = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, ticketId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Ticket(
                        resultSet.getInt("TicketID"),
                        resultSet.getInt("EventID"),
                        resultSet.getString("TicketBarcode"),
                        -1); // -1 or any placeholder for sjsuId
            }
        } catch (SQLException e) {
            System.out.println("DB operation failure. reason:\n");
            e.printStackTrace();
            // Handle exceptions appropriately later
        } finally {
            dbConnection.closeConnection();
        }
        return null;
    }


}
