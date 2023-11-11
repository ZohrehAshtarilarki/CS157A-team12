<%--
  Created by IntelliJ IDEA.
  User: zohrehashtarilarki
  Date: 10/26/23
  Time: 10:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Login</title>
</head>
<body>
<h1>Login</h1>
<form action="${pageContext.request.contextPath}/UserServlet" method="post">
  <input type="hidden" name="action" value="login">
  <div>
    <label for="username">Username:</label>
    <input type="text" name="username" id="username" required>
  </div>
  <div>
    <label for="password">Password:</label>
    <input type="password" name="password" id="password" required>
  </div>
  <div>
    <button type="submit">Login</button>
  </div>
</form>
<% String message = (String) request.getAttribute("message"); %>
<% if (message != null) { %>
<div class="error-message">
  <%= message %>
</div>
<% } %>
<!-- Use an absolute path instead of a relative path.
 This will ensure that no matter where the link is clicked from, it always redirects to the correct path. -->
<p>Don't have an account? <a href="${pageContext.request.contextPath}/views/registration.jsp">Create an Account</a></p>
</body>
</html>
