<%--
  Created by IntelliJ IDEA.
  User: zohrehashtarilarki
  Date: 11/26/23
  Time: 10:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Profile</title>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/header.css'>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/organizerProfile.css'>
    <script>
        // Function to show an alert if there's a message
        function showMessage() {
            <%
            String message = (String) session.getAttribute("message");
            if (message != null && !message.isEmpty()) {
            %>
            alert("<%= message %>"); // Show the message in an alert box
            <% session.removeAttribute("message"); // Remove the message from session %>
            <%
            }
            %>
        }
    </script>
</head>
<body onload="showMessage()"> <!-- Call showMessage when the page loads -->
<header>
    <nav>
        <ul>
            <li><a href="#">SJSUEvent</a></li>
            <li>
                <a href="${pageContext.request.contextPath}/views/home.jsp">Home</a>
                <a href="${pageContext.request.contextPath}/views/organizerDash.jsp">Dashboard</a>
                <a href="${pageContext.request.contextPath}/views/login.jsp">Log Out</a>
            </li>
        </ul>
    </nav>
</header>

<main>
    <h1>Update Your Profile</h1>
    <form action="${pageContext.request.contextPath}/EventOrganizerServlet" method="post">
        <input type="hidden" name="action" value="updateOrganizer">
        <div>
            <label for="sjsuId">SJSUID:</label>
            <input type="number" id="sjsuId" name="sjsuId" required>
        </div>
        <div>
            <label for="sjsuEmail">Email:</label>
            <input type="email" id="sjsuEmail" name="sjsuEmail" required>
        </div>
        <div>
            <label for="userName">Username:</label>
            <input type="text" id="userName" name="userName" required>
        </div>
        <div>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div>
            <div class="form-group">
                <label for="role">Role:</label>
                <input type="text" id="role" name="role" value="EventOrganizer" readonly>
            </div>
        </div>
        <div>
            <label for="organizationName">Organization Name:</label>
            <input type="text" id="organizationName" name="organizationName" required>
        </div>
        <div>
            <button type="submit" value="Update Profile">Update Profile</button>
        </div>
    </form>
</main>

</body>
</html>
