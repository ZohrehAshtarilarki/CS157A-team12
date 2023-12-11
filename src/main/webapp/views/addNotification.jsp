
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
  <h1>Add Notification</h1>
  <form action="${pageContext.request.contextPath}/NotificationServlet"
        method="post">
    <input type="hidden" name="action" value="addNotification">
    <div>
      <input type="text" name="eventID" id="eventID" value="<%=eventid%>">
    </div>
    <div>
      <label for="notificationText">Notification:</label> <input type="text" name="notificationText" id="notificationText" required>
    </div>
    <div>
      <button type="submit" name="action" value="addNotification">Add</button>
    </div>
  </form>
</main>
</body>
</html>