package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Event;
import model.EventOrganizer;
import model.User;
import util.DbConnectionInt;
import util.singletonDbConnection;

public class EventDAO{
    private final DbConnectionInt dbConnection;

    public EventDAO() {
        dbConnection = singletonDbConnection.getInstance();
    }

    public void createEvent(Event event, EventOrganizer eventOrganizer)
    {
        Connection connection = dbConnection.getConnection();
        String insertQuery = "INSERT INTO Event (EventID, EventName, Date, Time, Description, Category) VALUES (?,?,?,?,?,?)";
        String addManage = "INSERT INTO Manage (SJSUID, EventID) VALUES (?,?)";

        try {
            PreparedStatement ps1 = connection.prepareStatement(insertQuery);
            //EventID is auto-generated, we don't need to set it manually
            ps1.setInt(1, event.getEventID());
            ps1.setString(2, event.getEventName());
            ps1.setDate(3, event.getDate());
            ps1.setTime(4, event.getTime());
            ps1.setString(5, event.getDescription());
            ps1.setString(6, event.getCategory());

            PreparedStatement ps2 = connection.prepareStatement(addManage);
            ps2.setInt(1, eventOrganizer.getSjsuId());
            ps2.setInt(2, event.getEventID());

            ps1.executeUpdate();
            ps2.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally {
            dbConnection.closeConnection();
        }
    }

    /*

    public void editEvent(Event event, EventOrganizer eventOrganizer)
    {
    	Connection connection = dbConnection.getConnection();
    	String editQuery = "UPDATE Event NATURAL JOIN Manage SET eventName=?, date=?, time=?, description=?, category=? WHERE SJSUID = ? AND eventID = ?";

    	try {
    		PreparedStatement ps = connection.prepareStatement(editQuery);
    		ps.setString(1, event.getEventName());
    		ps.setDate(2, event.getDate());
    		ps.setTime(3, event.getTime());
    		ps.setString(4, event.getDescription());
    		ps.setString(5, event.getCategory());
    		ps.setInt(6, eventOrganizer.getSjsuId());
    		ps.setInt(7, event.getEventID());
    	} catch (SQLException e)
    	{
    		e.printStackTrace();
    	} finally {
    		dbConnection.closeConnection();
    	}
    }


    public void deleteEvent(Event event, EventOrganizer eventOrganizer)
    {
    	Connection connection = dbConnection.getConnection();
    	String deleteQuery = "DELETE FROM Event NATURAL JOIN Manage WHERE eventID=? AND SJSUID = ?)";

    	try {
    		PreparedStatement ps = connection.prepareStatement(deleteQuery);
    		ps.setInt(1, event.getEventID());
    		ps.setInt(2, eventOrganizer.getSjsuId());
    	} catch (SQLException e)
    	{
    		e.printStackTrace();
    	}
    }
    */

    public int deleteEvent(Event event, EventOrganizer eventOrganizer)
    {
        Connection connection = dbConnection.getConnection();
        String deleteQuery1 = "DELETE FROM Event WHERE EventID=? AND ? IN (SELECT SJSUID FROM Manage WHERE EventID=?);";
        String deleteQuery2 = "DELETE FROM Manage WHERE EventID=? AND SJSUID=?;";

        try {
            PreparedStatement ps1 = connection.prepareStatement(deleteQuery1);
            ps1.setInt(1, event.getEventID());
            ps1.setInt(2, eventOrganizer.getSjsuId());
            ps1.setInt(3, event.getEventID());

            int i = ps1.executeUpdate();
            if (i == 0)
            {
                return 0;
            }

            PreparedStatement ps2 = connection.prepareStatement(deleteQuery2);
            ps2.setInt(1, event.getEventID());
            ps2.setInt(2, eventOrganizer.getSjsuId());
            ps2.executeUpdate();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 1;
    }

    public boolean registerEvent(Event event, User user) {
        Connection connection = null;
        PreparedStatement registerStatement = null;
        PreparedStatement ticketStatement = null;
        PreparedStatement checkStatement;

        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // Check if user is already registered for the event
            String checkQuery = "SELECT COUNT(*) FROM Register WHERE SJSUID = ? AND EventID = ?";
            checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, user.getSjsuId());
            checkStatement.setInt(2, event.getEventID());

            ResultSet rs = checkStatement.executeQuery();
            // Retrieves the value of the first column in the current row
            if (rs.next() && rs.getInt(1) > 0) {
                // User is already registered for this event
                System.out.println("User is already registered for this event.");
                return false;
            }
            if (event.isRequiresTicket()) {
                // Register user for the event
                String registerQuery = "INSERT INTO Register (SJSUID, EventID, IsCheckedIn) VALUES (?, ?, ?)";
                registerStatement = connection.prepareStatement(registerQuery);
                registerStatement.setInt(1, user.getSjsuId());
                registerStatement.setInt(2, event.getEventID());
                registerStatement.setBoolean(3, false);
                registerStatement.executeUpdate();

                // Generate and insert a new ticket
                String ticketQuery = "INSERT INTO Ticket (EventID, TicketBarcode) VALUES (?, ?)";
                ticketStatement = connection.prepareStatement(ticketQuery);
                ticketStatement.setInt(1, event.getEventID());
                String ticketBarcode = util.TicketUtils.generateUniqueBarcode(); // Call to TicketUtils
                ticketStatement.setString(2, ticketBarcode);
                ticketStatement.executeUpdate();
            } else {
                String registerQuery = "INSERT INTO Register (SJSUID, EventID, IsCheckedIn) VALUES (?, ?, ?)";
                registerStatement = connection.prepareStatement(registerQuery);
                registerStatement.setInt(1, user.getSjsuId());
                registerStatement.setInt(2, event.getEventID());
                registerStatement.setBoolean(3, false);
                registerStatement.executeUpdate();
            }

            connection.commit(); // Commit transaction
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            // Close resources
            if (registerStatement != null) try { registerStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (ticketStatement != null) try { ticketStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return true;
    }


    public Event getEventById(int eventID) {
        Connection connection = dbConnection.getConnection();
        String selectQuery = "SELECT * FROM Event WHERE EventID = ?";
        Event event = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, eventID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                event = new Event();
                event.setEventID(Integer.parseInt(resultSet.getString("EventID")));
                event.setEventName(resultSet.getString("EventName"));
                event.setDate(resultSet.getDate("Date"));
                event.setTime(resultSet.getTime("Time"));
                event.setDescription(resultSet.getString("Description"));
                event.setCategory(resultSet.getNString("Category"));
                event.setRequiresTicket(resultSet.getBoolean("RequiresTicket"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        } finally {
            dbConnection.closeConnection();
        }

        return event;
    }

    public List<Event> getAllEvents() {
        Connection connection = dbConnection.getConnection();
        String selectQuery = "SELECT * FROM Event";
        List<Event> eventList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Event event = new Event();
                event.setEventID(Integer.parseInt(resultSet.getString("EventID")));
                event.setEventName(resultSet.getString("EventName"));
                event.setDate(resultSet.getDate("Date"));
                event.setTime(resultSet.getTime("Time"));
                event.setDescription(resultSet.getString("Description"));
                event.setCategory(resultSet.getNString("Category"));
                event.setRequiresTicket(resultSet.getBoolean("RequiresTicket"));

                eventList.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        } finally {
            dbConnection.closeConnection();
        }

        return eventList;
    }

    public Event getEventByName(String eventName) {
        String sql = "SELECT * FROM Event WHERE EventName = ?";
        Event event = null;

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, eventName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    event = new Event();
                    event.setEventID(Integer.parseInt(rs.getString("EventID")));
                    event.setEventName(rs.getString("EventName"));
                    event.setDate(rs.getDate("Date"));
                    event.setTime(rs.getTime("Time"));
                    event.setDescription(rs.getString("Description"));
                    event.setCategory(rs.getNString("Category"));
                    event.setRequiresTicket(rs.getBoolean("RequiresTicket"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        }
        return event;
    }
}