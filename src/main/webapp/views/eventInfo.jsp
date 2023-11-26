<%@ page import="model.Event"%>
<%@ page import="dal.EventDAO"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/createEvent.css">
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
		String eventIDParam = request.getParameter("eventID");
		String sjsuIDParam = request.getParameter("sjsuID");
		int eventID = 0;
		int sjsuID = 0;
		if (eventIDParam != null && !eventIDParam.isEmpty()) {
			try {
				eventID = Integer.parseInt(eventIDParam);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		if (sjsuIDParam != null && !sjsuIDParam.isEmpty()) {
			try {
				sjsuID = Integer.parseInt(sjsuIDParam);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		EventDAO eventDAO = new EventDAO();
		Event event = eventDAO.getEventById(eventID);
		int sjsuIDManage = eventDAO.getSJSUIDFromManageByEventID(eventID);
		System.out.println(eventID);
		System.out.println(sjsuIDManage);
		System.out.println(sjsuID);
		System.out.println(event);
		%>

		<div class="event-container">
			<div class="event-title"><%=event.getEventName()%></div>
			<div class="event-date">
				Date:
				<%=event.getDate()%></div>
			<div class="event-time">
				Time:
				<%=event.getTime()%></div>
			<div class="event-description">
				Description:
				<%=event.getDescription()%></div>
		</div>

		<%
		if (event.isRequiresTicket() && sjsuIDManage != sjsuID) {
		%>
		<div class="ticket-message">
			<p>This event requires a ticket. Please purchase to attend.</p>
			<!-- Purchase Ticket Button -->
			<form action="${pageContext.request.contextPath}/EventServlet"
				method="post">
				<input type="hidden" name="action" value="registerEvent">
				<div>
					<input type="hidden" name="sjsuId" id="sjsuidTicket"
						value="<%=sjsuID%>">
				</div>
				<div>
					<input type="hidden" name="eventId" value="<%=eventID%>">
				</div>
				<div>
					<button type="submit">Purchase Ticket</button>
				</div>
			</form>
		</div>
		<%
		} else if (!event.isRequiresTicket() && sjsuIDManage != sjsuID) {
		%>
		<div class="registration-message">
			<p>You can register for this event below:</p>
			<!-- Register Button -->
			<form action="${pageContext.request.contextPath}/EventServlet"
				method="post">
				<input type="hidden" name="action" value="registerEvent">
				<div>
					<input type="hidden" name="sjsuId" id="sjsuidRegister"
						value="<%=sjsuID%>">
				</div>
				<div>
					<input type="hidden" name="eventId" value="<%=eventID%>">
				</div>
				<div>
					<button type="submit">Register</button>
				</div>
			</form>
		</div>
		<%
		} else if (sjsuIDManage == sjsuID) {
		%>
		<form action="${pageContext.request.contextPath}/EventServlet"
			method="post">
			<input type="hidden" name="action" value="deleteEvent">
			<div>
				<input type="hidden" name="eventid" id="eventid"
					value="<%=eventID%>">
			</div>
			<div>
				<input type="hidden" name="sjsuid" id="sjsuid" value="<%=sjsuID%>">
			</div>
			<div>
				<button type="submit">Delete</button>
			</div>
		</form>

		<%
		}
		%>
	</main>
</body>
</html>
