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
  <link rel='stylesheet' href='${pageContext.request.contextPath}/css/header.css'>
  <link rel='stylesheet' href='${pageContext.request.contextPath}/css/login.css'>
</head>
<body>
<header>
  <nav>
    <ul>
      <li><a href="#">SJSUEvent</a></li>
      <li>
        <a href="${pageContext.request.contextPath}/views/login.jsp">Login</a>
        <a href="${pageContext.request.contextPath}/views/registration.jsp">Sign up</a>
      </li>
    </ul>
  </nav>
</header>

<div class="login-container">
  <h1>Login</h1>
  <form class="loginForm" action="${pageContext.request.contextPath}/UserServlet" method="post">
    <input type="hidden" name="action" value="login">
    <div class="form-group">
      <label for="username">Username:</label>
      <input type="text" name="username" id="username" required>
    </div>
    <div class="form-group">
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
</div>
</body>
</html>
