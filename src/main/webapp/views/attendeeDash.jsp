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
    String userID = (String) session.getAttribute("SJSUID");
    TicketDAO ticketDAO = new TicketDAO();
    List<Ticket> tickets = ticketDAO.getTicketsByUserID(userID);

    if (tickets == null || tickets.isEmpty()) {
        System.out.println("<p>No tickets available.</p>");
    } else {
        for (Ticket ticket : tickets) {
            // Display each ticket's details
            System.out.println("<div class='ticket'>");
            System.out.println("<p>Event ID: " + ticket.getEventId() + "</p>");
            System.out.println("<p>Ticket Barcode: " + ticket.getTicketBarcode() + "</p>");
            // Link to download the ticket
            System.out.println("<a href='/DownloadTicketServlet?ticketID=" + ticket.getTicketId() + "'>Download Ticket</a>");
            System.out.println("</div>");
        }
    }
%>

</body>
</html>

