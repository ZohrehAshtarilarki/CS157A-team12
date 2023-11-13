<%--
  Created by IntelliJ IDEA.
  User: zohrehashtarilarki
  Date: 10/26/23
  Time: 11:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List"%>
<%@ page import="model.Event"%>
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
                <a href="${pageContext.request.contextPath}/views/login.jsp">Log Out</a>
            </li>
        </ul>
    </nav>
</header>
<h1>Welcome to the Home Page</h1>

<!-- Trigger the retrieval of all events when the page loads -->
<form class="homeForm" action="${pageContext.request.contextPath}/EventServlet" method="get">
    <input type="hidden" name="action" value="getAllEvents">
    <input type="submit" style="display:none;">
</form>

<!-- Displaying the list of events -->
<section>
    <h2>Upcoming Events</h2>
    <ul>
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
        <li>
            <h3><%= event.getEventName() %></h3>
            <p>Date: <%= event.getDate() %></p>
            <p>Time: <%= event.getTime() %></p>
            <p>Description: <%= event.getDescription() %></p>
        </li>
        <%
            }
        } else {
        %>
        <p>No upcoming events.</p>
        <%
            }
        %>
    </ul>
</section>

<script>
    // Automatically submit the form to trigger the event list retrieval
    window.onload = function() {
        document.querySelector('.homeForm').submit();
    };
</script>

</body>
</html>
