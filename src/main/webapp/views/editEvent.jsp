<html>
<head>
    <title>Delete Event</title>
    <link rel='stylesheet'
          href='${pageContext.request.contextPath}/css/header.css'>
    <link rel='stylesheet'
          href='${pageContext.request.contextPath}/css/deleteEvent.css'>
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
    int eventid = 0;
    if (eventIDParam != null && !eventIDParam.isEmpty()) {
        try {
            eventid = Integer.parseInt(eventIDParam);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
%>

<main>
    <h1>Edit Event</h1>
    <form action="${pageContext.request.contextPath}/EventServlet" method="post">
        <input type="hidden" name="action" value="editEvent">
        <input type="hidden" name="eventID" id="eventID" value="<%=eventid%>">

        <div>
            <label for="eventName">Event Name:</label>
            <input type="text" name="eventName" id="eventName" required>
        </div>
        <div>
            <label for="eventDate">Event Date:</label>
            <input type="date" name="eventDate" id="eventDate" required>
        </div>
        <div>
            <label for="eventTime">Event time:</label>
            <input type="time" name="eventTime" id="eventTime" required>
        </div>
        <div>
            <label for="eventDescription">Event Description:</label>
            <input type="text" name="eventDescription" id="eventDescription" required>
        </div>
        <div>
            <label for="eventCategory">Event Category:</label>
            <input type="text" name="eventCategory" id="eventCategory" required>
        </div>
        <div>
            <label for="requiresTicket">Ticket Required:</label>
            <select name="requiresTicket" id="requiresTicket" required>
                <option value="true">True</option>
                <option value="false">False</option>
            </select>
        </div>

        <div>
            <button type="submit" name="action" value="editEvent">Edit</button>
        </div>
    </form>
</main>

</body>
</html>