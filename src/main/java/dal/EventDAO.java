package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Event;
import model.User;
import util.DbConnectionInt;
import util.singletonDbConnection;

public class EventDAO{
	private final DbConnectionInt dbConnection;

    public EventDAO() {
        dbConnection = singletonDbConnection.getInstance();
    }


    /*
    public void createEvent(Event event, EventOrganizer eventOrganizer)
    {
    	Connection connection = dbConnection.getConnection();
    	String insertQuery = "INSERT INTO Event (EventName, Date, Time, Description, Category) VALUES (?,?,?,?,?)";
    	String addManage = "INSERT INTO Manage (SJSUID, EventID) VALUES (?,?)";

    	try {
    		PreparedStatement ps1 = connection.prepareStatement(insertQuery);
            //EventID is auto-generated, we don't need to set it manually
    		//ps1.setInt(1, event.getEventID());
    		ps1.setString(1, event.getEventName());
    		ps1.setDate(2, event.getDate());
    		ps1.setTime(3, event.getTime());
    		ps1.setString(4, event.getDescription());
    		ps1.setString(5, event.getCategory());

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

    public void registerEvent(Event event, User user)
    {
    	Connection connection = dbConnection.getConnection();
        String checkinQuery = "INSERT INTO Register (SJSUID, EventID, IsCheckedIn) VALUES (?,?,?)";

    	try {
    		PreparedStatement ps = connection.prepareStatement(checkinQuery);
    		ps.setInt(1, user.getSjsuId());
    		ps.setInt(2, event.getEventID());
    		ps.setBoolean(3, false);
    		ps.executeUpdate();
    	}catch (SQLException e) {
    		e.printStackTrace();
    	} finally {
    		dbConnection.closeConnection();
    	}
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
}