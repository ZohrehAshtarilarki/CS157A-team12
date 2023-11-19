<%--
  Created by IntelliJ IDEA.
  User: zohrehashtarilarki
  Date: 10/26/23
  Time: 11:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="model.Ticket" %>
<%@ page import="dal.TicketDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Attendee Home Page</title>
</head>
<body>
<h1>Welcome to the Attendee Home Page</h1>

<%
    String userIDStr = (String) session.getAttribute("SJSUID");
    System.out.println("SJSUID: " + userIDStr); // Check if this prints the correct ID

    // Ensure userIDStr is not null and is a valid integer string
    // if the string contains only whitespace, it's treated as empty using trim()
    if (userIDStr != null && !userIDStr.trim().isEmpty()) {
        try {
            // Convert userID from a String to an int before passing it to the getTicketsByUserID method in the TicketDAO
            int userID = Integer.parseInt(userIDStr);
            TicketDAO ticketDAO = new TicketDAO();
            List<Ticket> tickets = ticketDAO.getTicketsByUserID(userID);
            System.out.println("Number of tickets retrieved: " + (tickets != null ? tickets.size() : "null"));
            if (tickets != null && !tickets.isEmpty()) {
                // Display each ticket's details
                for (Ticket ticket : tickets) {
%>
<div class='ticket'>
    <p>Event ID: <%= ticket.getEventId() %></p>
    <p>Ticket Barcode: <%= ticket.getTicketBarcode() %></p>
    <!-- Link to download the ticket -->
    <a href='${pageContext.request.contextPath}/DownloadTicketServlet?ticketID=<%= ticket.getTicketId() %>'>Download Ticket</a>
</div>
<%
    }
} else {
%>
<p>No tickets available.</p>
<%
            }
        } catch (NumberFormatException e) {
            // Handle the case where userIDStr is not a valid integer
            System.out.println("Error: Invalid userID format.");
        }
    } else {
        // Handle the case where userIDStr is null or empty
        System.out.println("Error: userID is null or empty.");
    }
%>

</body>
</html>