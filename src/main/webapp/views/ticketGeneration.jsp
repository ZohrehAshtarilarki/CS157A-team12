<%--
  Created by IntelliJ IDEA.
  User: zohrehashtarilarki
  Date: 11/13/23
  Time: 1:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Event" %>
<%@ page import="dal.EventDAO" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ticket Generation</title>
    <!-- Add any required CSS or JS links here -->
</head>
<body>
<h2>Ticket Generation Form</h2>
<form action="TicketServlet" method="post">
    <input type="hidden" name="action" value="generate" />

    <label for="eventId">Select Event:</label>
    <select name="eventId" id="eventId">
        <%
            EventDAO eventDAO = new EventDAO(); // EventDAO to handle event data
            List<Event> events = eventDAO.getEventsThatRequireTickets(); // Method to fetch events that require tickets
            for (Event event : events) {
                System.out.println("<option value='" + event.getEventID() + "'>" + event.getEventName() + "</option>");
                ();
            }
        %>
    </select>
    <br/>

    <%-- Include other fields if necessary --%>

    <input type="submit" value="Generate Ticket" />
</form>
</body>
</html>
