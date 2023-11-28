<%@ page import="model.Attendee" %>
<%@ page import="dal.AttendeeDAO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Event Organizer Dashboard</title>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/header.css'>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/organizerDash.css'>
</head>
<body>
<header>
    <nav>
        <ul>
            <li><a href="#">SJSUEvent</a></li>
            <li>
                <%
                    Integer sjsuId = (Integer) session.getAttribute("SJSUID");
                    if (sjsuId != null) {
                %>
                <a href="${pageContext.request.contextPath}/views/home.jsp">Home</a>
                <a href="${pageContext.request.contextPath}/views/organizerProfile.jsp">Profile</a>
                <a href="${pageContext.request.contextPath}/views/login.jsp">Log Out</a>
                <%
                    }
                %>
            </li>
        </ul>
    </nav>
</header>
<h1>Welcome to the Event Organizer Dashboard</h1>

<!-- Trigger/Open The Modal -->
<button id="btnShowAttendees">Delete Attendee</button>

<!-- The Modal -->
<div id="attendeeModal" class="modal">
    <!-- Modal content -->
    <div class="modal-content">
        <span class="close">&times;</span>
        <h2>Attendees</h2>
        <div class="attendee-list">
            <%
                AttendeeDAO attendeeDAO = new AttendeeDAO();
                List<Attendee> attendees = attendeeDAO.getAllAttendees();
                for (Attendee attendee : attendees) {
            %>
            <div class="attendee">
                <span><%= attendee.getUsername() %></span>
                <!-- Form for deletion -->
                <form action="${pageContext.request.contextPath}/AttendeeServlet" method="post"
                      onsubmit="return confirm('Are you sure you want to delete this attendee?');">
                    <input type="hidden" name="action" value="deleteAttendee">
                    <input type="hidden" name="sjsuId" value="<%= attendee.getSjsuId() %>">
                    <input type="submit" value="Delete">
                </form>
            </div>
            <%
                }
            %>
        </div>
    </div>
</div>

<% if (request.getAttribute("message") != null) { %>
<div class="message success"><%= request.getAttribute("message") %></div>
<% } %>
<% if (request.getAttribute("error") != null) { %>
<div class="message error"><%= request.getAttribute("error") %></div>
<% } %>

<script>
    // Get the modal
    var modal = document.getElementById("attendeeModal");

    // Get the button that opens the modal
    var btn = document.getElementById("btnShowAttendees");

    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];

    // When the user clicks the button, open the modal
    btn.onclick = function() {
        modal.style.display = "block";
    }

    // When the user clicks on <span> (x), close the modal
    span.onclick = function() {
        modal.style.display = "none";
    }

    // Close the modal if the user clicks outside of it
    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }
</script>

</body>
</html>
