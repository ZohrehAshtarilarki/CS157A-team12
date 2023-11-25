<%--
  Created by IntelliJ IDEA.
  User: zohrehashtarilarki
  Date: 10/26/23
  Time: 11:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List"%>
<%@ page import="model.Event"%>
<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/header.css'>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/home.css'>
</head>
<body>
<header>
    <nav>
        <ul>
            <li><a href="#">SJSUEvent</a></li>
            <li>
                <a href="${pageContext.request.contextPath}/views/home.jsp">Home</a>
               <%
                   Integer sjsuId = (Integer) session.getAttribute("SJSUID");
                   if (sjsuId != null) {

                %>

                <a href="${pageContext.request.contextPath}/views/attendeeDash.jsp">Dashboard</a>
                <%
                    }
                %>
                <a href="${pageContext.request.contextPath}/views/login.jsp">Log Out</a>
            </li>
        </ul>
    </nav>
</header>
<h1>Welcome to the Home Page</h1>

<div class="button-container">
			<button>
				<a href="${pageContext.request.contextPath}/views/createEvent.jsp">Create
					Event</a>
			</button>
			<button>
				<a href="${pageContext.request.contextPath}/views/deleteEvent.jsp">Delete
					Event</a>
			</button>
</div>
		
<!-- Trigger the retrieval of all events when the page loads -->
<form class="homeForm" action="${pageContext.request.contextPath}/EventServlet" method="get">
    <input type="hidden" name="action" value="getAllEvents">
    <input type="submit" style="display:none;">
</form>

<!-- Displaying the list of events -->
<h2>Upcoming Events</h2>
<div class="events-container">
        <%
            Object eventListObj = request.getAttribute("eventList");
            List<Event> events = null;

            if (eventListObj instanceof List<?>) {
                List<?> tempList = (List<?>) eventListObj;
                if (!tempList.isEmpty() && tempList.get(0) instanceof Event) {
                    events = (List<Event>) tempList;
                }
            }

            if (events != null && !events.isEmpty()) {
                for (Event event : events) {
        %>
        <ul id="event-list">
        <li class="event-item">
        <a href="${pageContext.request.contextPath}/views/eventInfo.jsp?eventID=<%=event.getEventID()%>">
            <div class="event-title"><%= event.getEventName() %></div>
            <div class="event-date"><%= event.getDate() %></div>
        </a>
        </li>
        </ul>
        <%
            }
        } else {
        %>
        <p>No upcoming events.</p>
        <%
            }
        %>
</div>

<script>
    // Automatically submit the form to trigger the event list retrieval
    window.onload = function() {
        document.querySelector('.homeForm').submit();
    };
</script>

</body>
</html>
