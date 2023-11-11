<%--
  Created by IntelliJ IDEA.
  User: zohrehashtarilarki
  Date: 10/26/23
  Time: 11:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up</title>
</head>
<body>
<h1>Create an Account</h1>
<form action="${pageContext.request.contextPath}/UserServlet" method="post">
    <input type="hidden" name="action" value="register">
    <div >
        <label for="sjsuId">SJSUID:</label>
        <input type="text" name="sjsuId" id="sjsuId" required>
    </div>
    <div>
        <label for="sjsuEmail">SJSUEmail:</label>
        <input type="email" name="sjsuEmail" id="sjsuEmail" required>
    </div>
    <div>
        <label for="username">Username:</label>
        <input type="text" name="username" id="username" required>
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" name="password" id="password" required>
    </div>
    <br> <!-- Line break added here -->
    <div>
        <label for="role">Role:</label>
        <select name="role" id="role" required>
            <option value="Attendee">Attendee</option>
            <option value="EventOrganizer">EventOrganizer</option>
        </select>
    </div>
    <br> <!-- Line break added here -->
    <div>
        <button type="submit" name="action" value="register">Register</button>
    </div>
</form>
<p>Already have an account? <a href="${pageContext.request.contextPath}/views/login.jsp">Login</a></p>
</body>
</html>