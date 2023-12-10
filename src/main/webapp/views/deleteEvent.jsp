<html>
<head>
	<title>Delete Event</title>
	<link rel='stylesheet'
		  href='${pageContext.request.contextPath}/css/header.css'>
	<link rel='stylesheet'
		  href='${pageContext.request.contextPath}/css/deleteEvent.css'>
</head>
<body>
<header>
	<nav>
		<ul>
			<li><a href="${pageContext.request.contextPath}/views/home.jsp">SJSUEvent</a></li>
			<li></li>
		</ul>
	</nav>
</header>

<main>
	<h1>Delete Event</h1>
	<form action="${pageContext.request.contextPath}/EventServlet"
		  method="post">
		<input type="hidden" name="action" value="deleteEvent">
		<div>
			<label for="eventid">Event ID:</label> <input type="text"
														  name="eventid" id="eventid" required>
		</div>
		<div>
			<label for="sjsuid">Organizer ID:</label> <input type="text"
															 name="sjsuid" id="sjsuid" required>
		</div>
		<div>
			<button type="submit" name="action" value="deleteEvent">Delete</button>
		</div>
	</form>
	<%
		String message = (String) request.getAttribute("message");
	%>
	<%
		if (message != null) {
	%>
	<div class="error-message">
		<%=message%>
	</div>
	<%
		}
	%>
</main>
</body>
</html>