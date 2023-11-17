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

    // Method to create a new ticket
    public boolean createTicket(Ticket ticket) {
        Connection connection = dbConnection.getConnection();
        String sql = "INSERT INTO Ticket (EventID, TicketBarcode) VALUES (?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ticket.getEventId());
            statement.setString(2, ticket.getTicketBarcode());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to retrieve a ticket by its barcode
    public Ticket getTicketByBarcode(String barcode) {
        Connection connection = dbConnection.getConnection();
        String sql = "SELECT * FROM Ticket WHERE TicketBarcode = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, barcode);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Ticket(
                        resultSet.getString("TicketID"),
                        resultSet.getString("EventID"),
                        resultSet.getString("TicketBarcode")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to retrieve tickets by user ID
    public List<Ticket> getTicketsByUserID(String sjsuID) {
        List<Ticket> tickets = new ArrayList<>();
        Connection connection = dbConnection.getConnection();
        /*
        Automatically join the Ticket and Register tables based on the common column(s).
        In this case, it's assumed that EventID is the common column.
         */
        String sql = "SELECT t.TicketID, t.EventID, t.TicketBarcode FROM Ticket t " +
                "NATURAL JOIN Register r " +
                "WHERE r.SJSUID = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, sjsuID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Ticket ticket = new Ticket(
                        resultSet.getString("TicketID"),
                        resultSet.getString("EventID"),
                        resultSet.getString("TicketBarcode")
                );
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return tickets;
    }

}
