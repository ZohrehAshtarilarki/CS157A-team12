<%--
  Created by IntelliJ IDEA.
  User: zohrehashtarilarki
  Date: 10/26/23
  Time: 11:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Event" %>
<%@ page import="model.Notification" %>
<%@ page import="dal.EventDAO" %>
<%@ page import="dal.NotificationDAO" %>

<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/header.css'>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/home.css'>
    <style>
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgb(0,0,0);
            background-color: rgba(0,0,0,0.4);
        }

        /* Style for the modal content */
        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 70%;
        }

        /* Close button style */
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
        .centered-container form.search-form {
            margin-top: 20px;
            text-align: center;
        }

        .search-form input[type="text"] {
            padding: 5px;
            margin-right: 10px;
            width: 200px; /* Adjust as necessary */
        }

        .search-form button {
            padding: 5px 15px;
        }
        .search-form select {
            padding: 5px;
            margin-right: 10px;
            width: auto; /* Adjust width as needed */
        }
        h2 {
            text-align: center;
        }
    </style>
</head>
<body>
<header>
    <nav>
        <ul>
            <li><a href="#">SJSUEvent</a></li>
            <li>
                <%-- Display the appropriate profile button based on the user type --%>
                <%
                    Integer sjsuId = (Integer) session.getAttribute("SJSUID");
                    String userType = (String) session.getAttribute("Role");
                    if (sjsuId != null) {
                        if ("Attendee".equals(userType)) {
                %>
                <a href="${pageContext.request.contextPath}/views/attendeeDash.jsp">Dashboard</a>
                <a href="${pageContext.request.contextPath}/views/attendeeProfile.jsp">Profile</a>
                <a onclick="openModal()"> Notification</a>
                <%
                } else if ("EventOrganizer".equals(userType)) {
                %>
                <a href="${pageContext.request.contextPath}/views/organizerDash.jsp">Dashboard</a>
                <a href="${pageContext.request.contextPath}/views/organizerProfile.jsp">Profile</a>
                <%
                    }
                %>
                <a href="${pageContext.request.contextPath}/views/login.jsp">Log Out</a>
                <%
                    }
                %>
            </li>
        </ul>
    </nav>
</header>
<h1>Welcome to the Home Page</h1>
<br>
<br>
<%
    NotificationDAO notificationDAO = new NotificationDAO();
    EventDAO eventDAO = new EventDAO();
    String searchCategory = request.getParameter("category");
    List<String> categories = eventDAO.getAllEventCategories();
    request.setAttribute("categories", categories);
%>
<div id="notificationModal" class="modal">
    <!-- Modal content -->
    <div class="modal-content">
        <span onclick="closeModal()" style="float: right; cursor: pointer;">&times;</span>
        <h2>Notifications</h2>
        <ul>
            <%
                List<Notification> notiList = notificationDAO.getAllNotification();
                for (Notification noti: notiList){
                    int eventid = noti.getEventID();
                    Event event = eventDAO.getEventById(eventid);

                    if (event != null){
            %>
            <li>
                <h3><%=event.getEventName()%></h3>=> <%=noti.getNotificationText()%>
            </li>
        </ul>
        <%
                }
            }
        %>
    </div>
</div>

<script>
    function openModal() {
        var modal = document.getElementById("notificationModal");
        modal.style.display = "block";
    }

    function closeModal() {
        var modal = document.getElementById("notificationModal");
        modal.style.display = "none";
    }

    // Close the modal if the user clicks outside of it
    window.onclick = function(event) {
        var modal = document.getElementById("notificationModal");
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }
</script>

<!-- Search Form -->
<div class="centered-container">
    <form action="${pageContext.request.contextPath}/EventServlet" method="GET" class="search-form">
        <input type="hidden" name="action" value="searchByCategory" />
        <label for="category">Search Events by Category:</label>
        <select name="category" id="category" required>
            <option value="">Select a category</option>
            <%
                for(String category : categories) {
                    // Select the searched category
                    String selected = category.equals(searchCategory) ? "selected" : "";
            %>
            <option value="<%= category %>" <%= selected %>><%= category %></option>
            <%
                }
            %>
        </select>
        <button type="submit">Search</button>
    </form>
</div>
<!-- Two line breaks -->
<br><br>

<% if(searchCategory == null || searchCategory.isEmpty()) { %>
<h2>Upcoming Events</h2>
<% } %>

<%
    List<Event> events;
    if (searchCategory != null && !searchCategory.isEmpty()) {
        events = eventDAO.getEventsByCategory(searchCategory);
    } else {
        events = eventDAO.getAllEvents();
    }
    request.setAttribute("events", events);
%>

<%
    // Attempt to get the 'events' attribute from the request.
    events = (List<Event>) request.getAttribute("events");

    // If 'events' is not set in the request, call getAllEvents() to display all events.
    if (events == null) {
        events = eventDAO.getAllEvents();
    }
%>
<div class="events-container">
    <%
        // Check if the 'events' list is not null and not empty.
        if (events != null && !events.isEmpty()) {
            // Iterate over each event and display its details.
            for (Event event : events) {
    %>
    <ul id="event-list">
        <li class="event-item">
            <a href="${pageContext.request.contextPath}/views/eventInfo.jsp?eventID=<%=event.getEventID()%>&sjsuID=<%=sjsuId%>">
                <div class="event-title"><%= event.getEventName() %></div>
                <div class="event-date"><%= event.getDate() %></div>
            </a>
        </li>
    </ul>
    <%
        }
    } else {
    %>
    <p>No events found.</p>
    <%
        }
    %>
</div>


</body>
</html>

