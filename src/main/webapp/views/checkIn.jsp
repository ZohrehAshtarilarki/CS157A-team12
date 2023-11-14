<%--
  Created by IntelliJ IDEA.
  User: zohrehashtarilarki
  Date: 11/13/23
  Time: 1:52 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Event Check-In</title>
  <!-- Add any required CSS or JS links here -->
</head>
<body>
<h2>Event Check-In</h2>
<form action="TicketServlet" method="post">
  <label for="ticketBarcode">Ticket Barcode:</label>
  <input type="text" id="ticketBarcode" name="ticketBarcode" required>
  <br/>

  <label for="eventId">Event ID:</label>
  <input type="text" id="eventId" name="eventId" required>
  <br/>

  <input type="submit" value="Check In" />
</form>
</body>
</html>
