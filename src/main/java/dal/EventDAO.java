package dal;

import util.DbConnectionInt;
import util.singletonDbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Event;
import model.EventOrganizer;

public class EventDAO{
	private final DbConnectionInt dbConnection;

    public EventDAO() {
        dbConnection = singletonDbConnection.getInstance();
    }
	/*
    public void createEvent(Event event, EventOrganizer eventOrganizer)
    {
    	Connection connection = dbConnection.getConnection();
    	String insertQuery = "INSERT INTO Event (eventID, eventName, date, time, description, category) VALUES (?,?,?,?,?) WHERE ? IN (SELECT SJSUID FROM Organizer)";
    	String addManage = "INSERT INTO Manage (SJSUID, eventID) VALUES (?,?)";
    	
    	try {
    		PreparedStatement ps1 = connection.prepareStatement(insertQuery);
    		ps1.setInt(1, event.getEventID());
    		ps1.setString(2, event.getEventName());
    		ps1.setDate(3, event.getDate());
    		ps1.setTime(4, event.getTime());
    		ps1.setString(5, event.getDescription());
    		ps1.setString(6, event.getCategory());
    		ps1.setInt(7, eventOrganizer.getSjsuId());
    		
    		PreparedStatement ps2 = connection.prepareStatement(addManage);
    		ps2.setInt(1, eventOrganizer.getSjsuId());
    		ps2.setInt(2, event.getEventID());
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
    public Event getEventById(int eventID) {
        Connection connection = dbConnection.getConnection();
        String selectQuery = "SELECT * FROM Event WHERE eventID = ?";
        Event event = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, eventID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                event = new Event();
                event.setEventID(Integer.parseInt(resultSet.getString("eventID")));
                event.setEventName(resultSet.getString("eventName"));
                event.setDate(resultSet.getDate("date"));
                event.setTime(resultSet.getTime("time"));
                event.setDescription(resultSet.getString("description"));
                event.setCategory(resultSet.getNString("category"));
                
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
                event.setEventID(Integer.parseInt(resultSet.getString("eventID")));
                event.setEventName(resultSet.getString("eventName"));
                event.setDate(resultSet.getDate("date"));
                event.setTime(resultSet.getTime("time"));
                event.setDescription(resultSet.getString("description"));
                event.setCategory(resultSet.getNString("category"));
                
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