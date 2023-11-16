<%@ page import="model.Event"%>
<%@ page import="dal.EventDAO"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href=" ${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet" href=" ${pageContext.request.contextPath}/css/createEvent.css">

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
	<form action="${pageContext.request.contextPath}/EventServlet"
		method="post">
		<input type="hidden" name="action" value="registerEvent">
		<div>
			<label for="sjsuid">SJSUID:</label> <input type="text" name="sjsuid"
				id="sjsuid" required>
		</div>
		<div>
			<label for="eventid"></label> <input type="text"
				name="eventid" id="eventid"  value="<%=eventID%>" style="display:none">
		</div>
		<div>
			<button type="submit" name="action" value="registerEvent">Register</button>
		</div>
	</form>
</main>
</body>
</html>
