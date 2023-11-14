<%--
  Created by IntelliJ IDEA.
  User: zohrehashtarilarki
  Date: 11/13/23
  Time: 1:50 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Ticket Validation</title>
  <!-- Add any required CSS or JS links here -->
</head>
<body>
<h2>Ticket Validation</h2>
<form action="TicketServlet" method="post">
  <input type="hidden" name="action" value="validate" />

  <label for="ticketBarcode">Enter Ticket Barcode:</label>
  <input type="text" id="ticketBarcode" name="ticketBarcode" required>
  <br/>

  <input type="submit" value="Validate Ticket" />
</form>
</body>
</html>

