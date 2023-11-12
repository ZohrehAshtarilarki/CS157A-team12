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
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
<nav>
  <div class="nav-brand">SAN JOSE STATE UNIVERSITY</div>
</nav>
<div class="login-container">
  <h1>EventSJSU Login</h1>
  <form class="login-form" action="${pageContext.request.contextPath}/UserServlet" method="post">
    <input type="hidden" name="action" value="login">
    <div class="input-group">
      <label for="username">Username</label>
      <input type="text" id="username" name="username" required>
    </div>
    <div class="input-group">
      <label for="password">Password</label>
      <input type="password" id="password" name="password" required>
    </div>
    <button type="submit">Login</button>
    <!-- Move this paragraph inside the login-container, below the button -->
    <p>Don't have an account? <a href="${pageContext.request.contextPath}/views/registration.jsp">Create an Account</a></p>
  </form>
  <% String message = (String) request.getAttribute("message"); %>
  <% if (message != null) { %>
  <div class="error-message">
    <%= message %>
  </div>
  <% } %>
</div>
</body>
</html>
