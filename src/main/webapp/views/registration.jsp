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
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/header.css'>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/registration.css'>
    <!--  use JavaScript to dynamically show or hide the input field based on the selected role  -->
    <script>
        //show or hide the "Organization Name" field based on the selected role
        function toggleOrganizationField() {
            var role = document.getElementById("role").value;
            var orgField = document.getElementById("organizationField");
            if (role === "EventOrganizer") {
                orgField.style.display = "block";
            } else {
                orgField.style.display = "none";
            }
        }
    </script>
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

<main>
    <h1>Create an Account</h1>
    <%-- Display error message if present using scriptlets--%>
    <% if (request.getAttribute("error") != null) { %>
    <div class="message error"><%= request.getAttribute("error") %></div>
    <% } %>

    <form action="${pageContext.request.contextPath}/UserServlet" method="post">
        <input type="hidden" name="action" value="registerUser">
        <div>
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
            <!-- onchange event listener calls this function whenever the user changes the selected role-->
            <select name="role" id="role" onchange="toggleOrganizationField()" required>
                <option value="Attendee">Attendee</option>
                <option value="EventOrganizer">EventOrganizer</option>
            </select>
        </div>
        <br> <!-- Line break added here -->
        <!-- Organization Name Field, initially hidden and will only be displayed if "EventOrganizer" is selected. -->
        <div id="organizationField" style="display:none;">
            <label for="organizationName">Organization Name:</label>
            <input type="text" name="organizationName" id="organizationName">
        </div>
        <br> <!-- Line break added here -->
        <div>
            <button type="submit" name="action" value="register">Register</button>
        </div>
    </form>
    <p>Already have an account? <a href="${pageContext.request.contextPath}/views/login.jsp">Login</a></p>
    <script>
        // Ensure the field is set correctly if the page reloads
        toggleOrganizationField();
    </script>
</main>

</body>
</html>
