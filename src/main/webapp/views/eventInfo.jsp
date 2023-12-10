
<%@ page import="model.Event"%>
<%@ page import="dal.EventDAO"%>
<%@ page import="dal.TicketDAO" %>
<%@ page import="dal.ReviewDAO" %>
<%@ page import="dal.CommentDAO"%>
<%@ page import="model.Review"%>
<%@ page import="model.Comment"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/createEvent.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/comment.css">


	<title>Event Details</title>
	<style>

		.event-container {
			background-color: #fff;
			box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
			padding: 20px;
			text-align: center;
		}
		body {
			font-family: 'Times New Roman', sans-serif;
			margin: 0;
			padding: 0;
			background-color: #f4f4f4;
		}

		/* Styles for Review Form */

		.review-form h3 {
			margin-bottom: 15px;
			color: #333;
		}

		.review-form form {
			display: flex;
			flex-direction: column;
			align-items: center;
		}

		.form-group {
			margin-bottom: 15px;
			width: 100%; /* Adjust as needed */
		}

		.form-group label {
			display: block;
			margin-bottom: 5px;
			color: #333;
		}

		.form-group textarea {
			width: 100%; /* Adjust as needed */
			padding: 10px;
			border: 1px solid #ddd;
			border-radius: 4px;
			resize: vertical; /* Allow vertical resizing */
		}

		.form-group select {
			width: 50%; /* Adjust as needed */
			padding: 10px;
			border: 1px solid #ddd;
			border-radius: 4px;
			background-color: white;
			cursor: pointer;
		}

		/* Style for Submit Button in Review Form */
		.review-form button {
			background-color: #007bff;
			color: #fff;
			padding: 10px 20px;
			font-size: 16px;
			border: none;
			border-radius: 4px;
			cursor: pointer;
			transition: background-color 0.3s ease;
		}

		.review-form button:hover {
			background-color: #0056b3;
		}


		.star-rating {
			display: inline-block;
		}

		.star {
			cursor: pointer;
			color: rgb(128, 128, 128);
			font-size: 30px;
		}

		.star:hover,
		.star:hover ~ .star {
			color: gold;
		}

		.success-message {
			color: green;
			text-align: center;
			/* Additional styling as needed */
		}

		.error-message {
			color: red;
			text-align: center;
		}

		/* Star Rating System */
		.star-rating {
			display: inline-block;
			font-size: 24px;
			color: #cccccc; /* Default color */
			cursor: pointer;
		}

		.star-rating .star {
			font-size: 30px;
			color: #cccccc; /* Inactive stars */
		}

		/* Hover styles */
		.star-rating .star:hover,
		.star-rating .star:hover ~ .star {
			color: gold;
		}

		.star-rating .star.rated {
			color: gold; /* Active stars */
		}

		.review-star-rating {
			font-size: 24px;
			color: rgb(128, 128, 128);
		}

		.review-star-rating span.review-star {
			position: relative;
			display: inline-block; /* Make sure it contains the absolute positioned :before element */
			font-size: 30px;
			color: #cccccc;
		}
		.review-star-rating span.review-star.full {
			color: rgb(255,215,0); /* Filled stars in gold */
		}
		.review-star-rating span.review-star.half:before {
			content: '\2605';
			position: absolute;
			left: 0;
			top: 0;
			width: 50%;
			overflow: hidden;
			color: rgb(255,215,0); /* Half star in gold */
			font-size: 30px;
		}
		.register-button {
			background-color: #0055a8;
			color: #fff;
			padding: 10px 20px;
			font-size: 16px;
			border: none;
			cursor: pointer;
			transition: background-color 0.3s ease;
		}

		.register-button:hover {
			background-color: #f7ca18;
		}
		.purchase-ticket {
			background-color:#0055a8;
			color: #fff;
			padding: 10px 20px;
			font-size: 16px;
			border: none;
			cursor: pointer;
			transition: background-color 0.3s ease; /* Apply transition to the background color */
		}
		.purchase-ticket:hover {
			background-color: #f7ca18; /* Change background color to yellow */
		}
		.modal {
			display: none; /* Hidden by default */
			position: fixed; /* Stay in place */
			z-index: 1; /* Sit on top */
			left: 0;
			top: 0;
			width: 100%; /* Full width */
			height: 100%; /* Full height */
			overflow: auto; /* Enable scroll if needed */
			background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
		}

		.modal-content {
			background-color: #fefefe;
			margin: 15% auto; /* 15% from the top and centered */
			padding: 20px;
			border: 1px solid #888;
			width: 80%; /* Could be more or less, depending on screen size */
		}

		.close {
			color: #aaa;
			float: right;
			font-size: 28px;
			font-weight: bold;
		}

		.close:hover,
		.close:focus {
			color: black;
			text-decoration: none;
			cursor: pointer;
		}
		.reviews-container {
			width: 100%; /* Make the reviews container take the full width of its parent */
			display: flex;
			flex-direction: column;
			align-items: center; /* Align children (reviews) in the center */
			justify-content: flex-start; /* Align children (reviews) to the start of the flex container */
		}

		.review {
			background-color: #fff; /* Or any color you prefer */
			margin: 10px 0; /* Spacing between each review, with no side margins */
			padding: 20px;
			border: 1px solid #ddd; /* For a subtle border */
			border-radius: 5px; /* For rounded corners */
			box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* Optional shadow for depth */
			width: calc(100% - 40px); /* Take the full width of the container minus padding */
			box-sizing: border-box; /* Include padding and border in the width */
		}

		.review-text {
			font-size: 14px; /* Adjust font size as necessary */
			line-height: 1.5; /* For better readability */
			text-align: left; /* Left align the review text */
		}

	</style>
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
	<br> <!-- Line break added here -->

	<!-- Trigger/Open The Reviews Modal Button -->
	<button type="button" id="reviewsButton">Reviews</button>

	<!-- The Modal -->
	<div id="reviewsModal" class="modal">
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
					%>
					<span class="review-star full">&#9733;</span>
					<%
					} else if (i - averageRating < 1) {
					%>
					<span class="review-star half">&#9733;</span>
					<%
					} else {
						System.out.println("Here none");
					%>
					<%
							}
						}
					%>
				</div>
				<div class="event-rating">
					<p>Average Rating: <%= String.format("%.2f", averageRating) %> (<%= ratingCount %> ratings)</p>
				</div>
				<div class="reviews-container">
					<%
						String eventIDString = request.getParameter("eventID") != null ? request.getParameter("eventID") : request.getParameter("eventId");
						//int eventID = 0;
						Integer eventID = Integer.parseInt(eventIDString);
						List<Review> reviews;
						if(eventID!=null) {
							reviews = reviewDAO.getReviewById(eventID);

							for(Review review : reviews) {
					%>
					<div class="review">
						<p><%= review.getReviewText() %></p>
					</div>
					<% }} %>
				</div>

				<!-- Insert dynamic list of reviews here -->
				<% if (!"EventOrganizer".equalsIgnoreCase(userRole)) { %>
				<button id="writeReviewButton">Write a Review</button>
				<% } %>
			</div>
			<div id="writeReviewForm">
				<h2>Write a Review</h2>
				<form action="${pageContext.request.contextPath}/ReviewServlet" method="post">
					<input type="hidden" name="action" value="saveReview">
					<input type="hidden" name="eventID" value="<%=eventID%>">
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
		//String eventIDString = request.getParameter("eventID") != null ? request.getParameter("eventID") : request.getParameter("eventId");
		//int eventID = 0;
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

	<div class="ticket-message">
		<p>This event requires a ticket. Please purchase to attend.</p>
		<form action="${pageContext.request.contextPath}/EventServlet" method="post">
			<input type="hidden" name="action" value="registerEvent">
			<label for="sjsuidTicket">SJSUID:</label>
			<input type="text" name="sjsuId" id="sjsuidTicket" required>
			<input type="hidden" name="eventId" value="<%=eventID%>">
			<button type="submit" class="purchase-ticket">Purchase Ticket</button>
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
		//String eventIDString = request.getParameter("eventID") != null ? request.getParameter("eventID") : request.getParameter("eventId");
		//int eventID = 0;
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
			<button type="submit" class="register-button">Register</button>
		</form>
	</div>
	<%
	}
	else{
	%>
	<div class="registration-message">
		<p class="error-message">You have already registered for this event.</p>
	</div>
	<%
				}}
		}
	%>

		<div class="comment-box">
			<h1>Comment</h1>
			<%
				//String eventIDString = request.getParameter("eventID") != null ? request.getParameter("eventID") : request.getParameter("eventId");
				//int eventID = 0;
				eventID = Integer.parseInt(eventIDString);
				CommentDAO commentDAO = new CommentDAO();
				List<Comment> comments = commentDAO.getAllCommentbyEvent(eventID);

				if (comments != null && !comments.isEmpty()) {
					for (Comment comment : comments) {
			%>
			<ul id="comment-list">
				<li class="comment-item"><a>
					<div class="comment-title"><%=comment.getCommentText()%></div>
				</a></li>
			</ul>
			<%
				}
			} else {
			%>
			<p>No Comment.</p>
			<%
				}
			%>

			<form action="${pageContext.request.contextPath}/CommentServlet"
				  method="post">
				<input type="hidden" name="action" value="addComment">
				<div>
					<input type="hidden" name="eventID" id="eventID" value=<%=eventID%>>
				</div>
				<div>
					<input type="hidden" name="sjsuID" id="sjsuID" value=<%=sjsuId%>>
				</div>
				<div>
					<input type="text" name="commentText" id="commentText"
						   placeHolder="Leave Your Comment Here" required>
				</div>
				<div>
					<button type="submit" name="action" value="addComment">Comment</button>
				</div>
			</form>
		</div>

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
		if (writeReviewBtn) { // Added a check to ensure writeReviewBtn exists
			writeReviewBtn.onclick = function() {
				reviewsSection.style.display = "none";
				writeReviewForm.style.display = "block";
			}
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
				stars.forEach(function(s, idx) {
					if (idx <= index) {
						s.classList.add('rated');
					} else {
						s.classList.remove('rated');
					}
				});
			};
		});
	});
</script>

</body>
</html>
