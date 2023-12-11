
<%@ page import="model.Attendee" %>
<%@ page import="dal.AttendeeDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="dal.ReviewDAO" %>
<%@ page import="model.Review" %>
<%@ page import="dal.EventDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="model.Event" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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
                <a href="${pageContext.request.contextPath}/views/home.jsp">Home</a>
                <a href="${pageContext.request.contextPath}/views/organizerProfile.jsp">Profile</a>
                <a href="${pageContext.request.contextPath}/views/login.jsp">Log Out</a>
                <%
                    Integer sjsuId = (Integer) session.getAttribute("SJSUID");
                    if (sjsuId != null) {
                %>
                <%
                    }
                %>
            </li>
        </ul>
    </nav>
</header>
<h1>Welcome to the Event Organizer Dashboard</h1>
<br> <!-- Line break added here -->
<br> <!-- Line break added here -->
<br> <!-- Line break added here -->

<!-- Button Group for Deletion and Creation Actions -->
<div class="button-group">
    <!-- Trigger/Open The Create Event Modal -->
    <button id="btnCreateEvent">Create Event</button>

    <!-- Trigger/Open The Attendee Delete Modal -->
    <button id="btnShowAttendees">Delete Attendee</button>

    <!-- Trigger/Open The Review Delete Modal -->
    <button id="btnShowReviews">Delete Review</button>

    <!-- Trigger/Open The Registered Attendee Modal -->
    <button id="btnViewRegisteredAttendees">Registered Attendees</button>
</div>

<!-- The Create Event Modal -->
<div id="createEventModal" class="modal">
    <div class="modal-content">
        <span class="close-create">&times;</span>
        <iframe src="${pageContext.request.contextPath}/views/createEvent.jsp?sjsuID=<%=sjsuId%>" style="width:100%; height:80vh; border:none;"></iframe>
    </div>
</div>

<!-- The Attendee Delete Modal -->
<div id="attendeeDeleteModal" class="modal">
    <!-- Modal content for Deleting Attendee -->
    <div class="modal-content">
        <span class="close-attendee">&times;</span>
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

<!-- The Review Delete Modal -->
<div id="reviewModal" class="modal">
    <!-- Modal content -->
    <div class="modal-content">
        <span class="close-review">&times;</span>
        <h2>Reviews</h2>
        <div class="review-list">
            <%
                ReviewDAO reviewDAO = new ReviewDAO();
                List<Review> reviews = null;
                try {
                    if (sjsuId != null) {
                    reviews = reviewDAO.getReviewsByOrganizer(sjsuId);
                } }catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                for (Review review : reviews) {
            %>
            <div class="review">
                <span><%= review.getReviewText() %></span>
                <!-- Form for review deletion -->
                <form action="${pageContext.request.contextPath}/ReviewServlet" method="post"
                      onsubmit="return confirm('Are you sure you want to delete this review?');">
                    <input type="hidden" name="action" value="deleteReview">
                    <input type="hidden" name="reviewId" value="<%= review.getReviewId() %>">
                    <input type="submit" value="Delete">
                </form>
            </div>
            <%
                }
            %>
        </div>
    </div>
</div>

<!-- The Registered Attendees Modal -->
<div id="registeredAttendeesModal" class="modal">
    <!-- Modal content -->
    <div class="modal-content">
        <span class="close-registered-attendees">&times;</span>
        <h2>Registered Attendees</h2>
        <div class="event-attendee-list">
            <%
                HashMap<String, Integer> eventToCountMap = new EventDAO().getAttendeeCountForEvent((Integer) session.getAttribute("SJSUID"));
                for (String event: eventToCountMap.keySet()) {
            %>

            <div class="event-entry">
                <span class="event-name"><%= event %></span>
                <span class="attendee-count"><%= eventToCountMap.get(event) %> attendees</span>
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
    // Script for Attendee Delete Modal
    var deleteModal = document.getElementById("attendeeDeleteModal");
    var btnDelete = document.getElementById("btnShowAttendees");
    var spanDelete = document.getElementsByClassName("close-attendee")[0];

    btnDelete.onclick = function() {
        deleteModal.style.display = "block";
    }
    // When the user clicks on <span> (x), close the delete modal
    spanDelete.onclick = function() {
        deleteModal.style.display = "none";
    }
    // Close the review modal if the user clicks outside of it
    window.onclick = function(event) {
        if (event.target === deleteModal) {
            deleteModal.style.display = "none";
        }
    }

    // Get the review modal
    var reviewModal = document.getElementById("reviewModal");
    // Get the button that opens the review modal
    var btnShowReviews = document.getElementById("btnShowReviews");
    // Get the <span> element that closes the review modal
    var spanReview = document.getElementsByClassName("close-review")[0];

    // When the user clicks the button, open the review modal
    btnShowReviews.onclick = function() {
        reviewModal.style.display = "block";
    }

    // When the user clicks on <span> (x), close the review modal
    spanReview.onclick = function() {
        reviewModal.style.display = "none";
    }
    // Close the review modal if the user clicks outside of it
    window.onclick = function(event) {
        if (event.target === reviewModal) {
            reviewModal.style.display = "none";
        }
    }

    // Script for Registered Attendees Modal
    var registeredAttendeesModal = document.getElementById("registeredAttendeesModal");
    var btnRegisteredAttendees = document.getElementById("btnViewRegisteredAttendees");
    var spanCloseRegisteredAttendees = document.getElementsByClassName("close-registered-attendees")[0];

    btnRegisteredAttendees.onclick = function() {
        registeredAttendeesModal.style.display = "block";
    }

    spanCloseRegisteredAttendees.onclick = function() {
        registeredAttendeesModal.style.display = "none";
    }

    // Add to the existing window.onclick function
    window.onclick = function(event) {
        // Existing modal close logic...
        if (event.target === registeredAttendeesModal) {
            registeredAttendeesModal.style.display = "none";
        }
    }

    // Get the modal for create event
    var modal = document.getElementById("createEventModal");

    // Get the button that opens the modal
    var btn = document.getElementById("btnCreateEvent");

    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close-create")[0];

    // When the user clicks the button, open the modal
    btn.onclick = function() {
        modal.style.display = "block";
    }

    // When the user clicks on <span> (x), close the modal
    span.onclick = function() {
        modal.style.display = "none";
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }
</script>
<%
    EventDAO eventDAO = new EventDAO();
    List<Integer> list = eventDAO.getEventListBySjsuID(sjsuId);
%>
<h2>My Events</h2>
<%
    for (Integer id: list)
    {
        Event event = eventDAO.getEventById(id);
%>
<div class="attendee">
    <span class="event-name"><%=event.getEventName()%></span>
    <div class="button-group">
        <form action="${pageContext.request.contextPath}/EventServlet" method="post" class="event-form">
            <input type="hidden" name="eventId" value="<%=event.getEventID()%>">
            <input type="hidden" name="sjsuId" value="<%=sjsuId%>">
            <button type="submit" name="action" value="deleteEvent" class="event-button delete-button">Delete</button>
        </form>
        <a href="${pageContext.request.contextPath}/views/editEvent.jsp?eventID=<%=event.getEventID()%>" class="event-button edit-button">Edit</a>
        <a href="${pageContext.request.contextPath}/views/addNotification.jsp?eventID=<%=event.getEventID()%>"><button>Add Notification</button></a>
    </div>
</div>
<%}%>

</body>
</html>
