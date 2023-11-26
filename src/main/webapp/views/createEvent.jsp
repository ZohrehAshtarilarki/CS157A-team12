<html>
<head>
<title>Create Event</title>
<link rel='stylesheet' href='${pageContext.request.contextPath}/css/header.css'>
<link rel='stylesheet' href='${pageContext.request.contextPath}/css/createEvent.css'>
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

	<main>
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
		<h1>Create Event</h1>
		<form action="${pageContext.request.contextPath}/EventServlet"
			method="post">
			<input type="hidden" name="action" value="createEvent">
			<div> 
				<input type="hidden" name="sjsuId" id="sjsuId" value=<%=sjsuid%>>
			</div>
			<div>
				<label for="eventName">Event Name:</label> 
				<input type="text" name="eventName" id="eventName" required>
			</div>
			<div>
				<label for="eventDate">Event Date:</label> 
				<input type="date" name="eventDate" id="eventDate" required>
			</div>
			<div>
				<label for="eventTime">Event time:</label> 
				<input type="time" name="eventTime" id="eventTime" required>
			</div>
			<div>
				<label for="eventDescription">Event Description:</label> 
				<input type="text" name="eventDescription" id="eventDescription" required>
			</div>
			<div>
				<label for="eventCategory">Event Category:</label> 
				<input type="text" name="eventCategory" id="eventCategory" required>
			</div>
			<div>
            <label for="requiresTicket">Ticket Required:</label>
            <select name="requiresTicket" id="requiresTicket" required>
                <option value="1">True</option>
                <option value="0">False</option>
            </select>
        </div>
			<div>
				<button type="submit" name="action" value="createEvent">Create</button>
			</div>
		</form>
	</main>
</body>
</html>