<%@ page import="model.Event"%>
<%@ page import="dal.EventDAO"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
	<title>Event Details</title>
</head>
<body>
<header>
	<nav>
		<ul>
			<li><a href="${pageContext.request.contextPath}/views/home.jsp">SJSUEvent</a></li>
			<li></li>
		</ul>
	</nav>
</header>
<%
	String eventIDParam = request.getParameter("eventID");
	int eventID = 0;
	if (eventIDParam != null && !eventIDParam.isEmpty()) {
		try {
			eventID = Integer.parseInt(eventIDParam);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	EventDAO eventDAO = new EventDAO();
	Event event = eventDAO.getEventById(eventID);
%>

<div class="event-container">
	<div class="event-title"><%= event.getEventName() %></div>
	<div class="event-date">Date: <%= event.getDate() %></div>
	<div class="event-time">Time: <%= event.getTime() %></div>
	<div class="event-description">Description: <%= event.getDescription() %></div>
</div>

<% if (event.isRequiresTicket()) { %>
<div class="ticket-message">
	<p>This event requires a ticket. Please purchase to attend.</p>
	<!-- Purchase Ticket Button -->
	<form action="${pageContext.request.contextPath}/EventServlet" method="post">
		<input type="hidden" name="action" value="registerEvent">
		<div>
			<label for="sjsuidTicket">SJSUID:</label> <input type="text" name="sjsuId" id="sjsuidTicket" required>
		</div>
		<div>
			<input type="hidden" name="eventId" id="eventidTicket" value="<%=eventID%>">
		</div>
		<div>
			<button type="submit">Purchase Ticket</button>
		</div>
	</form>
</div>
<% } else { %>
<div class="registration-message">
	<p>You can register for this event below:</p>
	<!-- Register Button -->
	<form action="${pageContext.request.contextPath}/EventServlet" method="post">
		<input type="hidden" name="action" value="registerEvent">
		<div>
			<label for="sjsuidRegister">SJSUID:</label> <input type="text" name="sjsuId" id="sjsuidRegister" required>
		</div>
		<div>
			<input type="hidden" name="eventId" id="eventidRegister" value="<%=eventID%>">
		</div>
		<div>
			<button type="submit">Register</button>
		</div>
	</form>
</div>
<% } %>


</body>
</html>