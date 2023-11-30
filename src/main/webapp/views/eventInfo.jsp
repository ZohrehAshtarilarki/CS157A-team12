
<%@ page import="model.Event"%>
<%@ page import="dal.EventDAO"%>
<%@ page import="dal.TicketDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Comment" %>
<%@ page import="dal.ReviewDAO" %>


<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/createEvent.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/comment.css">
	
	<script src="${pageContext.request.contextPath}/js/script.js"></script>

	<script src="${pageContext.request.contextPath}/js/script.js"></script>
	<title>Event Details</title>
</head>
<body>
<header>
	<nav>
		<ul>
			<li><a href="${pageContext.request.contextPath}/views/home.jsp">SJSUEvent</a></li>
			<li>
				<a href="${pageContext.request.contextPath}/views/attendeeDash.jsp">Dashboard</a>
				<a href="${pageContext.request.contextPath}/views/attendeeProfile.jsp">Profile</a>
				<a href="${pageContext.request.contextPath}/views/home.jsp">Home</a>
				<a href="${pageContext.request.contextPath}/views/login.jsp">Log Out</a>
			</li>
		</ul>
	</nav>
</header>
<main>
	<%-- Display Success and Error Messages --%>
	<% if (request.getAttribute("successMessage") != null) { %>
	<p class="success-message"><%= request.getAttribute("successMessage") %></p>
	<% } %>
	<% if (request.getAttribute("errorMessage") != null) { %>
	<p class="error-message"><%= request.getAttribute("errorMessage") %></p>
	<% } %>

	<%
		// Retrieve sjsuId from session
		Integer sjsuId = (Integer) session.getAttribute("SJSUID");
		String userRole = (String) session.getAttribute("Role");

		// Attempt to obtain the event ID directly from the request's query parameter
		Integer eventId = null;
		String eventIDParam = request.getParameter("eventID");
		if (eventIDParam != null && !eventIDParam.trim().isEmpty()) {
			try {
				eventId = Integer.parseInt(eventIDParam);
			} catch (NumberFormatException e) {
				// Log the exception and set an error message for invalid event ID format
				e.printStackTrace();
				request.setAttribute("errorMessage", "Invalid format for event ID.");
			}
		}
		
		Event event = null; // Declare the event variable
		// Proceed to fetch event details from the database if a valid event ID is obtained
		if (eventId != null) {
			EventDAO eventDAO = new EventDAO();
			event = eventDAO.getEventById(eventId);  // Initialize the event variable

			// Check if the event object is not null and display its details
			if (event != null) {
	%>
	<div class="event-container">
		<div class="event-title"><%= event.getEventName() %></div>
		<div class="event-date">Date: <%= event.getDate() %></div>
		<div class="event-time">Time: <%= event.getTime() %></div>
		<div class="event-description">Description: <%= event.getDescription() %></div>
	</div>
	<%
	} else {
		// If event is null, display an error message indicating event details are not found
	%>
	<p class="error-message">Error: Event details not found.</p>
	<%
			}
		}
	%>
		<br> <!-- Line break added here -->

		<!-- Trigger/Open The Reviews Modal Button -->
		<button type="button" id="reviewsButton">Reviews</button>

		<!-- The Modal -->
		<div id="reviewsModal" class="modal" style="display: none">
			<!-- Modal content -->
			<div class="modal-content">
				<span class="close">&times</span>
				<div id="reviewsSection">
					<h2>Event Reviews</h2>
					<div class="review-star-rating">
						<%
							ReviewDAO reviewDAO = new ReviewDAO();
							double averageRating = 0.0;
							int ratingCount = 0;
							if (eventId != null) {
								averageRating = reviewDAO.getAverageRatingForEvent(Integer.parseInt(String.valueOf(eventId)));
								ratingCount = reviewDAO.getRatingCountForEvent(Integer.parseInt(String.valueOf(eventId)));
								System.out.println("average rating: " + averageRating);
							} else {
								// Handle the case where eventId is null
								System.out.println("Event ID is null. Cannot retrieve average rating.");
							}

							System.out.println("average rating: " + averageRating);
							for (int i = 1; i <= 5; i++) {
								System.out.println(i);
								if (i <= averageRating) {
									System.out.println("Here full");
						%>
						<span class="review-star full">&#9733;</span>
						<%
						} else if (i - averageRating < 1) {
							System.out.println("Here half");
						%>
						<span class="review-star half">&#9733;</span>
						<%
						} else {
							System.out.println("Here none");
						%>
<%--						<span class="review-star">&#9733;</span>--%>
						<%
								}
							}
						%>
					</div>
					<div class="event-rating">
						<p>Average Rating: <%= String.format("%.2f", averageRating) %> (<%= ratingCount %> ratings)</p>
					</div>

<%--					<p>Number of Reviews: <!-- Insert dynamic number of reviews --></p>--%>
<%--					<p>Average Rating: <!-- Insert dynamic average rating --></p>--%>
					<!-- Insert dynamic list of reviews here -->
					<% if (!"EventOrganizer".equalsIgnoreCase(userRole)) { %>
						<button id="writeReviewButton">Write a Review</button>
					<% } %>
				</div>
				<div id="writeReviewForm" style="display:none;">
					<h2>Write a Review</h2>
					<form onclick="{document.getElementById('writeReviewButton').style.visibility = 'hidden'}" action="${pageContext.request.contextPath}/ReviewServlet" method="post">
						<input type="hidden" name="action" value="saveReview">
						<input type="hidden" name="eventId" value="<%=eventId%>">
						<input type="hidden" name="sjsuId" value="<%=sjsuId%>">
						<div class="form-group">
							<label for="reviewText">Review:</label>
							<textarea name="reviewText" id="reviewText" rows="4" required></textarea>
						</div>
						<!-- Star Rating System -->
						<div class="form-group">
							<label>Rating:</label>
							<div class="star-rating">
								<span class="star" data-value="1">&#9733;</span>
								<span class="star" data-value="2">&#9733;</span>
								<span class="star" data-value="3">&#9733;</span>
								<span class="star" data-value="4">&#9733;</span>
								<span class="star" data-value="5">&#9733;</span>
							</div>
							<input type="hidden" name="rating" id="ratingInput" value="0">
						</div>
						<button type="submit">Post</button>
					</form>
				</div>
			</div>
		</div>
		<%
			TicketDAO ticketDAO = new TicketDAO();
			boolean ticketExists = false;

			if (sjsuId != null && eventId != null) {
				ticketExists = ticketDAO.hasTicketForEvent(sjsuId, eventId);
			}
			if (!"EventOrganizer".equalsIgnoreCase(userRole)) {
				if (event != null && event.isRequiresTicket()) {
					if (ticketExists) {
		%>
		<p class="error-message">Ticket already exists for this event and user.</p>
		<%
		} else {
			String eventIDString = request.getParameter("eventID") != null ? request.getParameter("eventID") : request.getParameter("eventId");
			int eventID = 0;
			try {
				eventID = Integer.parseInt(eventIDString);
			} catch (NumberFormatException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid event ID");
				return;
			}

		<%-- Retrieve sjsuId from session and add it as a hidden field --%>
		<% Integer sjsuId = (Integer) session.getAttribute("sjsuId");%>
		<% if (sjsuId != null) { %>
		<input type="hidden" name="sjsuId" value="<%=sjsuId%>">
		<% } %>
		
		<%  
		String sjsuIDParam = request.getParameter("sjsuID");
		int sjsuid = 0;
		if (sjsuIDParam != null && !sjsuIDParam.trim().isEmpty()) {
			try {
				sjsuid = Integer.parseInt(sjsuIDParam);
			} catch (NumberFormatException e) {
				// Log the exception and set an error message for invalid event ID format
				e.printStackTrace();
				request.setAttribute("errorMessage", "Invalid format for event ID.");
			}
		}
		%>

		<!-- Submit Rating Button -->
		<button type="button" onclick="submitRating()">Submit Rating</button>
	</form>

	<%
		TicketDAO ticketDAO = new TicketDAO();
		boolean ticketExists = false;
		
		if (sjsuId != null && eventId != null) {
			// Check if the ticket already exists for this user and event
			ticketExists = ticketDAO.hasTicketForEvent(sjsuId, eventId);
		}
		if (event != null && event.isRequiresTicket()) {
			if (ticketExists) {
	%>
	<p class="error-message">Ticket already exists for this event and user.</p>
	<%
	} else {
	%>
	<div class="ticket-message">
		<p>This event requires a ticket. Please purchase to attend.</p>
		<!-- Purchase Ticket Button -->
		<form action="${pageContext.request.contextPath}/EventServlet" method="post">
			<input type="hidden" name="action" value="registerEvent">
			<div>
				<input type="hidden" name="sjsuId" id="sjsuId" value="<%=sjsuid%>">
			</div>
			<div>
				<input type="hidden" name="eventId" value="<%=eventId%>">
			</div>
			<div>
				<button type="submit">Purchase Ticket</button>
			</div>
		</form>
	</div>
	<%
		}
	} else { %>
	<div class="registration-message">
		<p>You can register for this event below:</p>
		<!-- Register Button -->
		<form action="${pageContext.request.contextPath}/EventServlet" method="post">
			<input type="hidden" name="action" value="registerEvent">
			<div>
				<input type="hidden" name="sjsuId" id="sjsuId" value="<%=sjsuid%>">
			</div>
			<div>
				<input type="hidden" name="eventId" id="eventId" value="<%=eventId%>">
			</div>
			<div>
				<button type="submit">Register</button>
			</div>
		</form>
	</div>
	<% } %>
	
	
	<form class="commentForm" action="${pageContext.request.contextPath}/CommentServlet" method="get">
    <input type="hidden" name="action" value="getAllCommentByEvent">
    <input type="hidden" name="eventID" id="eventID" value="<%=eventId%>">
    <input type="hidden" name="sjsuID" id="sjsuID" value="<%=sjsuid%>">
    
    <input type="submit" style="display:none;">
	</form>
	<div class="comment-box">
	<h1>Comment</h1>
	<%
        Object commentListObj = request.getAttribute("commentList");
        List<Comment> comments = null;

        if (commentListObj instanceof List<?>) {
            List<?> tempList = (List<?>) commentListObj;
            if (!tempList.isEmpty() && tempList.get(0) instanceof Comment) {
                comments = (List<Comment>) tempList;
            }
        }

        if (comments != null && !comments.isEmpty()) {
            for (Comment comment : comments) {
            	
    %>
        <ul id="comment-list">
        <li class="comment-item">
            <a>
                <div class="comment-title"><%= comment.getCommentText() %></div>
            </a>
        </li>
    </ul>
    <%
        }
    } else {
    %>
    <p>No Comment.</p>
    <%
        }
    %>
    <script>
    // Automatically submit the form to trigger the event list retrieval
    window.onload = function() {
        document.querySelector('.commentForm').submit();
    };
	</script>
    
    <form action="${pageContext.request.contextPath}/CommentServlet"
			method="post">
			<input type="hidden" name="action" value="addComment">
			<div>
				 <input type="hidden"
					name="eventID" id="eventID" value=<%=eventId%>>
			</div>
			<div>
				 <input type="hidden"
					name="sjsuID" id="sjsuID" value=<%=sjsuid %>>
			</div>
			<div>
				 <input type="text"
					name="commentText" id="commentText" placeHolder="Leave Your Comment Here" required>
			</div>
			<div>
				<button type="submit" name="action" value="addComment">Comment</button>
			</div>
		</form>
	</div>
	
			int user = (Integer) session.getAttribute("SJSUID");
			EventDAO eventDAO = new EventDAO();
			boolean isUserRegistered = eventDAO.isUserRegisteredForEvent(user, eventID);
			System.out.println("User Registered: " + isUserRegistered);
			if (!isUserRegistered){
		%>
		<div class="ticket-message">
			<p>This event requires a ticket. Please purchase to attend.</p>
			<form action="${pageContext.request.contextPath}/EventServlet" method="post">
				<input type="hidden" name="action" value="registerEvent">
				<label for="sjsuidTicket">SJSUID:</label>
				<input type="text" name="sjsuId" id="sjsuidTicket" required>
				<input type="hidden" name="eventId" value="<%=eventID%>">
				<button type="submit">Purchase Ticket</button>
			</form>
		</div>
		<%
				}
			else{
			%>
		<div class="ticket-message">
			<p class="error-message">Ticket already exists for this event and user.</p>
		</div>
		<%}
			}
		} else {
			String eventIDString = request.getParameter("eventID") != null ? request.getParameter("eventID") : request.getParameter("eventId");
			int eventID = 0;
			try {
				eventID = Integer.parseInt(eventIDString);
			} catch (NumberFormatException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid event ID");
				return;
			}

			int user = (Integer) session.getAttribute("SJSUID");
			EventDAO eventDAO = new EventDAO();
			boolean isUserRegistered = eventDAO.isUserRegisteredForEvent(user, eventID);
			System.out.println("User Registered: " + isUserRegistered);

			if (!isUserRegistered){
		%>
		<div class="registration-message">
			<p>You can register for this event below:</p>
			<form action="${pageContext.request.contextPath}/EventServlet" method="post">
				<input type="hidden" name="action" value="registerEvent">
				<label for="sjsuidRegister">SJSUID:</label>
				<input type="text" name="sjsuId" id="sjsuidRegister" required>
				<input type="hidden" name="eventId" value="<%=eventID%>">
				<button type="submit">Register</button>
			</form>
		</div>
		<%
	}
	else{
	%>
		<div class="registration-message">
			<p class="error-message">You have already registered for this event.</p>
		</div>
		<%}}
			}
		%>
</main>
<script>
	// Wait until the DOM is fully loaded
	document.addEventListener('DOMContentLoaded', function() {
		// Get the modal element
		var reviewsModal = document.getElementById("reviewsModal");

		// Get buttons that control the modal
		var reviewsBtn = document.getElementById("reviewsButton");
		var writeReviewBtn = document.getElementById("writeReviewButton");

		// Get the <span> element that closes the modal
		var span = document.getElementsByClassName("close")[0];

		span.onclick = function() {
			var modal = document.getElementById("reviewsModal");
			modal.style.display = "none";
		};
		// Sections within the modal
		var reviewsSection = document.getElementById("reviewsSection");
		var writeReviewForm = document.getElementById("writeReviewForm");

		// When the user clicks the button, open the reviews section
		reviewsBtn.onclick = function() {
			reviewsModal.style.display = "block";
			reviewsSection.style.display = "block";
			writeReviewForm.style.display = "none";
		}

		// When the user clicks the button, show the write review form
		writeReviewBtn.onclick = function() {
			reviewsSection.style.display = "none";
			writeReviewForm.style.display = "block";
		}

		// When the user clicks on <span> (x), close the modal
		span.onclick = function() {
			reviewsModal.style.display = "none";
		}

		// Close the modal if the user clicks anywhere outside of it
		window.onclick = function(event) {
			if (event.target === reviewsModal) {
				reviewsModal.style.display = "none";
			}
		}

		// JavaScript for Star Rating
		var stars = document.querySelectorAll('.star');
		var ratingInput = document.getElementById('ratingInput');

		stars.forEach(function(star, index) {
			star.onclick = function() {
				// Set the rating input value
				ratingInput.value = index + 1;

				// Update star colors
				stars.forEach(function(star, idx) {
					if (idx <= index) {
						star.classList.add('rated');
					} else {
						star.classList.remove('rated');
					}
				});
			};
		});
	});

</script>
</body>
</html>
