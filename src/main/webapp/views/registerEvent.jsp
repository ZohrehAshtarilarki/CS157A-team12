<html>
<head>
<title>Register Event</title>
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
		<h1>Register Event</h1>
		<form action="${pageContext.request.contextPath}/EventServlet"
			method="post">
			<input type="hidden" name="action" value="registerEvent">
			<div>
				<label for="sjsuid">SJSUID:</label> <input type="text" name="sjsuid"
					id="sjsuid" required>
			</div>
			<div>
				<label for="eventid">Event ID:</label> <input type="text"
					name="eventid" id="eventid" required>
			</div>
			<div>
				<button type="submit" name="action" value="registerEvent">Register</button>
			</div>
		</form>
	</main>
</body>
</html>