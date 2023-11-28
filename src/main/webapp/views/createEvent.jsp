<html>
<head>
<title>Create Event</title>
<link rel='stylesheet'
	href='${pageContext.request.contextPath}/css/header.css'>
<link rel='stylesheet'
	href='${pageContext.request.contextPath}/css/createEvent.css'>
</head>
<body>
	<header>
		<nav>
			<ul>
				<li><a href="#">SJSUEvent</a></li>
				<li></li>
			</ul>
		</nav>
	</header>
	<%
	String sjsuIDParam = request.getParameter("sjsuID");
	int sjsuid = 0;
	if (sjsuIDParam != null && !sjsuIDParam.isEmpty()) {
		try {
			sjsuid = Integer.parseInt(sjsuIDParam);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	%>

	<main>
		<h1>Create Event</h1>
		<form action="${pageContext.request.contextPath}/EventServlet"
			method="post">
			<input type="hidden" name="action" value="createEvent">
			<div>
				<input type="hidden" name="sjsuId" id="sjsuId" value=<%=sjsuid%>>
			</div>
			<div>
				<label for="eventname">Event Name:</label> <input type="text"
					name="eventName" id="eventname" required>
			</div>
			<div>
				<label for="eventdate">Event Date:</label> <input type="date"
					name="eventDate" id="eventdate" required>
			</div>
			<div>
				<label for="eventtime">Event time:</label> <input type="time"
					name="eventTime" id="eventtime" required>
			</div>
			<div>
				<label for="eventdescription">Event Description:</label> <input
					type="text" name="eventDescription" id="eventdescription" required>
			</div>
			<div>
				<label for="eventcategory">Event Category:</label> <input
					type="text" name="eventCsategory" id="eventcategory" required>
			</div>
			<label for="requiresTicket">Ticket Required:</label> <select
				name="requiresTicket" id="requiresTicket" required>
				<option value="true">True</option>
				<option value="false">False</option>
			</select>

			<div>
				<button type="submit" name="action" value="createEvent">Create</button>
			</div>
		</form>
	</main>
</body>
</html>