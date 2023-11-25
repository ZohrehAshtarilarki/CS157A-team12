

<%@ page import="model.Event"%>
<%@ page import="dal.EventDAO"%>
<%@ page import="dal.AttendeeDAO"%>
<%@ page import="dal.TicketDAO" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/createEvent.css">
	<script src="${pageContext.request.contextPath}/js/script.js"></script>

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
<main>
	<%
		// Attempt to obtain the event ID from the request attribute first
		Integer eventId = (Integer) request.getAttribute("eventId");

		// If it's not found, then retrieve it from the request's query parameter
		if (eventId == null) {
			String eventIDParam = request.getParameter("eventID");
			if (eventIDParam != null && !eventIDParam.trim().isEmpty()) {
				try {
					eventId = Integer.parseInt(eventIDParam);
				} catch (NumberFormatException e) {
					// Log the exception and set an error message for invalid event ID format
					e.printStackTrace();
					request.setAttribute("errorMessage", "Invalid format for event ID.");
				}
			}
		}

		Event event = null; // Declare the event variable
		// Proceed to fetch event details from the database if a valid event ID is obtained
		if (eventId != null) {
			EventDAO eventDAO = new EventDAO();
			event = eventDAO.getEventById(eventId);  // Initialize the event variable

			// Check if the event object is not null and display its details
			if (event != null) {
	%>
	<div class="event-container">
		<div class="event-title"><%= event.getEventName() %></div>
		<div class="event-date">Date: <%= event.getDate() %></div>
		<div class="event-time">Time: <%= event.getTime() %></div>
		<div class="event-description">Description: <%= event.getDescription() %></div>
	</div>
	<%
	} else {
		// If event is null, display an error message indicating event details are not found
	%>
	<p class="error-message">Error: Event details not found.</p>
	<%
			}
		}
	%>


<%-- Success and Error Message Display for Submitting Review--%>
	<% if (request.getAttribute("successMessage") != null) { %>
		<p class="success-message"><%= request.getAttribute("successMessage") %></p>
	<% } %>
	<% if (request.getAttribute("errorMessage") != null) { %>
		<p class="error-message"><%= request.getAttribute("errorMessage") %></p>
	<% } %>

	<br> <!-- Line break added here -->

	<!-- Reviews Button and 5-Star Rating System -->
	<div class="reviews-section">
		<button type="button" id="reviewsButton">Reviews</button>
		<div class="star-rating">
			<span class="star" data-value="1">&#9733;</span>
			<span class="star" data-value="2">&#9733;</span>
			<span class="star" data-value="3">&#9733;</span>
			<span class="star" data-value="4">&#9733;</span>
			<span class="star" data-value="5">&#9733;</span>
		</div>
	</div>

	<form id="ratingForm" action="${pageContext.request.contextPath}/SubmitRatingServlet" method="post">
		<input type="hidden" name="eventId" value="<%=eventId%>">
		<input type="hidden" name="rating" id="ratingInput" value="0">

		<%-- Retrieve sjsuId from session and add it as a hidden field --%>
		<% Integer sjsuId = (Integer) session.getAttribute("sjsuId"); %>
		<% if (sjsuId != null) { %>
		<input type="hidden" name="sjsuId" value="<%=sjsuId%>">
		<% } %>

		<!-- Submit Rating Button -->
		<button type="button" onclick="submitRating()">Submit Rating</button>
	</form>

		<%
		TicketDAO ticketDAO = new TicketDAO();
		boolean ticketExists = false;

		if (sjsuId != null && eventId != null) {
		// Check if the ticket already exists for this user and event
		ticketExists = ticketDAO.hasTicketForEvent(sjsuId, eventId);
		}

		if (event != null && event.isRequiresTicket()) {
		if (ticketExists) {
		%>
		<p class="error-message">Ticket already exists for this event and user.</p>
			<%
        } else {
%>
		<div class="ticket-message">
			<p>This event requires a ticket. Please purchase to attend.</p>
			<!-- Purchase Ticket Button -->
			<form action="${pageContext.request.contextPath}/EventServlet" method="post">
				<input type="hidden" name="action" value="registerEvent">
				<div>
					<label for="sjsuidTicket">SJSUID:</label> <input type="text" name="sjsuId" id="sjsuidTicket" required>
				</div>
				<div>
					<input type="hidden" name="eventId" value="<%=eventId%>">
				</div>
				<div>
					<button type="submit">Purchase Ticket</button>
				</div>
			</form>
		</div>
			<%
        }
    } else { %>
	<div class="registration-message">
		<p>You can register for this event below:</p>
		<!-- Register Button -->
		<form action="${pageContext.request.contextPath}/EventServlet" method="post">
			<input type="hidden" name="action" value="registerEvent">
			<div>
				<label for="sjsuidRegister">SJSUID:</label> <input type="text" name="sjsuId" id="sjsuidRegister" required>
			</div>
			<div>
				<input type="hidden" name="eventId" value="<%=eventId%>">
			</div>
			<div>
				<button type="submit">Register</button>
			</div>
		</form>
	</div>
	<% } %>
</main>
</body>
</html>