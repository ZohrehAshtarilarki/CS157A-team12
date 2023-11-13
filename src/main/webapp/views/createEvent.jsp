<html>
<head>
<title>Create Event</title>
<link rel='stylesheet' href='header.css'>
<link rel='stylesheet' href='registeration.css'>
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
		<h1>Create Event</h1>
		<form action="${pageContext.request.contextPath}/EventServlet"
			method="post">
			<input type="hidden" name="action" value="createEvent">
			<div>
				<label for="eventid">Event ID:</label> 
				<input type="text" name="eventid" id="eventid" required>
			</div>
			<div>
				<label for="sjsuid">Organizer ID:</label> 
				<input type="text" name="sjsuid" id="sjsuid" required>
			</div>
			<div>
				<label for="eventname">Event Name:</label> 
				<input type="text" name="eventname" id="eventname" required>
			</div>
			<div>
				<label for="eventdate">Event Date:</label> 
				<input type="date" name="eventdate" id="eventdate" required>
			</div>
			<div>
				<label for="eventtime">Event time:</label> 
				<input type="time" name="eventtime" id="eventtime" required>
			</div>
			<div>
				<label for="eventdescription">Event Description:</label> 
				<input type="text" name="eventdescription" id="eventdescription" required>
			</div>
			<div>
				<label for="eventcategory">Event Category:</label> 
				<input type="text" name="eventcategory" id="eventcategory" required>
			</div>
			<div>
				<button type="submit" name="action" value="createEvent">Create</button>
			</div>
		</form>
	</main>
</body>
</html>